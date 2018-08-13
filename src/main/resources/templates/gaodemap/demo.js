$(function(){

    //浏览器当前窗口文档body的高度
    var height = $(document.body).height();
    $("#map").css("height",(height-5)+"px");
    $("#result").css("height",(height-100)+"px");
    $("#address").val("");
    $("#result").on("click","li",function(){
        var point = $(this).find(".point").text();
        $("#s-point").text(point);//赋值
        $("#result li").css("background-color","#fff");
        $(this).css("background-color","#f0f0f0");
    });
    //绑定input文本框回车事件
    $('#address').bind('keypress',function(event){
        if(event.keyCode == "13"){
            doSearch();//搜索
        }
    });
});
//高德地图API功能
var map = new AMap.Map('map', {
    resizeEnable: true,
    zoom:11,
    center: [116.397428, 39.90923]
});
var markers = [];//定义标注数组
//为地图注册click事件获取鼠标点击出的经纬度坐标
var clickEventListener = map.on('click', function(e) {
    var lng = e.lnglat.getLng();
    var lat = e.lnglat.getLat();
    geocoder2(lng,lat);
});

function geocoder2(key_11,key_12) {  //根据经纬度获取地址
    var lnglatXY = new AMap.LngLat(key_11,key_12);
    //加载地理编码插件
    map.plugin(["AMap.Geocoder"], function() {
        MGeocoder = new AMap.Geocoder({
            radius: 1000,
            extensions: "all"
        });
        //返回地理编码结果
        AMap.event.addListener(MGeocoder, "complete", geocoder_CallBack2);
        //逆地理编码
        MGeocoder.getAddress(lnglatXY);
    });
    map.remove(markers);//查询前先移除所有标注
    //加点
    marker = new AMap.Marker({
        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
        position:[key_11,key_12]
    });
    markers.push(marker);
    marker.setMap(map);
    map.setFitView();
}

function geocoder_CallBack2(data) { //回调函数,根据经纬度获取周边信息返回详情
    var address;
    address = data.regeocode.formattedAddress;
    document.getElementById("result").innerHTML = address;

}
/*
function geocoder_CallBack2(data) { //回调函数,根据经纬度获取周边信息返回详情
    var roadinfo ="";
    var poiinfo="";
    var address;
    //返回地址描述
    address = data.regeocode.formattedAddress;
    //返回周边道路信息
    roadinfo +="<table style='width:600px'>";
    for(var i=0;i<data.regeocode.roads.length;i++){
        var color = (i % 2 === 0 ? '#fff' : '#eee');
        roadinfo += "<tr style='background-color:" + color + "; margin:0; padding:0;'><td>道路：" + data.regeocode.roads[i].name + "</td><td>方向：" + data.regeocode.roads[i].direction + "</td><td>距离：" + data.regeocode.roads[i].distance + "米</td></tr>";
    }
    roadinfo +="</table>"
    //返回周边兴趣点信息
    poiinfo += "<table style='width:600px;cursor:pointer;'>";
    for(var j=0;j<data.regeocode.pois.length;j++){
        var color = j % 2 === 0 ? '#fff' : '#eee';
        poiinfo += "<tr onmouseover='onMouseOver(\"" + data.regeocode.pois[j].location.toString() + "\")' style='background-color:" + color + "; margin:0; padding:0;'><td>兴趣点：" + data.regeocode.pois[j].name + "</td><td>类型：" + data.regeocode.pois[j].type + "</td><td>距离：" + data.regeocode.pois[j].distance + "米</td></tr>";
    }
    poiinfo += "</table>";
    //返回结果拼接输出
    resultStr = "<div style=\"font-size: 12px;padding:0px 0 4px 2px; border-bottom:1px solid #C1FFC1;\">"+"<b>地址</b>："+ address + "<hr/><b>周边道路信息</b>：<br/>" + roadinfo + "<hr/><b>周边兴趣点信息</b>：<br/>" + poiinfo +"</div>";
    document.getElementById("result").innerHTML = resultStr;
}*/

var placeSearch = new AMap.PlaceSearch();  //构造地点查询类
//地址查询,根据地质名字查询地地址
function doSearch(){
    var address = document.getElementById("address").value;
    placeSearch.search(address, function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
            var poiArr = result.poiList.pois;
            //设置地图显示级别及中心点
            map.setZoomAndCenter(14,new AMap.LngLat(poiArr[0].location.lng,poiArr[0].location.lat));
            //获取查询城市信息
            map.getCity(function(res){
                $("#s-city").text(res.province+res.city+poiArr[0].address);
                $("#address_arg").val(res.province+res.city);
            });
            //加点
            map.remove(markers);//查询前先移除所有标注
            marker = new AMap.Marker({
                icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                position:[poiArr[0].location.lng,poiArr[0].location.lat]
            });
            markers.push(marker);
            marker.setMap(map);
            map.setFitView();

            var str = "<ul>";
            for(var i=0;i<poiArr.length;i++){
                str+='<li onclick="doSearch1(\''+poiArr[i].address+'\')">';
                str+='<div class="res-data">';
                str+='<div class="left res-marker">';
                str+='<span>'+String.fromCharCode(65+i)+'</span>';
                str+='</div>';
                str+='<div class="left res-address">';
                str+='<div class="title">'+poiArr[i].name+'</div>';
                str+='<div>地址：<span class="rout">'+poiArr[i].address+'</span></div>';
                str+='</div>';
                str+='<div class="clearfix"></div>';
                str+='</div>';
                str+='</li>';
            }
            str+='</ul>';
            console.info(str);
            $("#result").html(str);
        }
    });
}

//为地图注册click事件获取鼠标点击出的经纬度坐标,给地图设置mark标记
/*var clickEventListener = map.on('click', function(e) {
    marker = new AMap.Marker({
        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
        position:[e.lnglat.getLng(),e.lnglat.getLat()]
    });
    marker.setMap(map);
});*/


function doSearch1(address){
    placeSearch.search(address, function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
            var poiArr = result.poiList.pois;
            $("#result").html("");
            //设置地图显示级别及中心点
            map.setZoomAndCenter(14,new AMap.LngLat(poiArr[0].location.lng,poiArr[0].location.lat));
            //获取查询城市信息
            map.getCity(function(res){
                console.info(res);
                $("#s-city").text(res.province+res.city+poiArr[0].address);
            });
            //加点
            map.remove(markers);//查询前先移除所有标注
            marker = new AMap.Marker({
                icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
                position:[poiArr[0].location.lng,poiArr[0].location.lat]
            });
            markers.push(marker);
            marker.setMap(map);
            map.setFitView();
        }
    });
}