package com.example.springbootwebdemo.ueditor.ueditor.hunter;

import com.example.springbootwebdemo.ueditor.ueditor.PathFormat;
import com.example.springbootwebdemo.ueditor.ueditor.define.AppInfo;
import com.example.springbootwebdemo.ueditor.ueditor.define.BaseState;
import com.example.springbootwebdemo.ueditor.ueditor.define.MultiState;
import com.example.springbootwebdemo.ueditor.ueditor.define.State;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;

	public FileManager(Map<String, Object> conf) {

		this.rootPath = (String) conf.get("rootPath");
		this.dir = this.rootPath + (String) conf.get("dir");
		this.allowFiles = this.getAllowFiles(conf.get("allowFiles"));
		this.count = (Integer) conf.get("count");

	}

	public State listFile(int index) {

		File dir = new File(this.dir);
		State state = null;

		if (!dir.exists()) {
			return new BaseState(false, AppInfo.NOT_EXIST);
		}

		if (!dir.isDirectory()) {
			return new BaseState(false, AppInfo.NOT_DIRECTORY);
		}

		Collection<File> list = FileUtils.listFiles(dir, this.allowFiles, true);

		if (index < 0 || index > list.size()) {
			state = new MultiState(true);
		} else {
			Object[] fileList = Arrays.copyOfRange(list.toArray(), index, index + this.count);
			state = this.getState(fileList);
		}

		state.putInfo("start", index);
		state.putInfo("total", list.size());

		return state;

	}

	private State getState(Object[] files) {

		MultiState state = new MultiState(true);
		BaseState fileState = null;

		File file = null;

		for (Object obj : files) {
			if (obj == null) {
				break;
			}
			file = (File) obj;
			fileState = new BaseState(true);

			// ========== 百度源码的又一个坑 ==========
			// ========== 解决原始输出完整路径的情况 ==========
			String fileFullPath = PathFormat.format(this.getPath(file));// 把路径转换为标准路径
			String rootPathTemp = "";
			if (rootPath.startsWith("/")) {
				rootPathTemp = rootPath.substring(1); // 去除路径以"/"开始的部分
			} else {
				rootPathTemp = rootPath;
			}

			fileState.putInfo("url", PathFormat.format(fileFullPath.replace(rootPathTemp, "")));
			state.addState(fileState);
		}

		return state;

	}

	private String getPath(File file) {

		String path = file.getAbsolutePath();

		return path.replace(this.rootPath, "/");

	}

	private String[] getAllowFiles(Object fileExt) {

		String[] exts = null;
		String ext = null;

		if (fileExt == null) {
			return new String[0];
		}

		exts = (String[]) fileExt;

		for (int i = 0, len = exts.length; i < len; i++) {

			ext = exts[i];
			exts[i] = ext.replace(".", "");

		}

		return exts;

	}

}
