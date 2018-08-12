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

//为地图注册click事件获取鼠标点击出的经纬度坐标
var clickEventListener = map.on('click', function(e) {
    document.getElementById("s-point").innerHTML = e.lnglat.getLng() + ',' + e.lnglat.getLat();
    marker = new AMap.Marker({
        icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png",
        position:[e.lnglat.getLng(),e.lnglat.getLat()]
    });
    marker.setMap(map);
});


var placeSearch = new AMap.PlaceSearch();  //构造地点查询类
var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});//信息窗口
var markers = [];//定义标注数组
//地址查询
function doSearch(){
    map.remove(markers);//查询前先移除所有标注
    var address = document.getElementById("address").value;
    placeSearch.search(address, function(status, result) {
        if (status === 'complete' && result.info === 'OK') {
//               alert(JSON.stringify(result));
            var poiArr = result.poiList.pois;
            var str = "<ul>";
            for(var i=0;i<poiArr.length;i++){
                //在地图上创建标注点
                marker = new AMap.Marker({
                    icon: "http://webapi.amap.com/theme/v1.3/markers/n/mark_b.png"
                });
                marker.setPosition(new AMap.LngLat(poiArr[i].location.lng,poiArr[i].location.lat));
                marker.setMap(map);
                marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
                    offset: new AMap.Pixel(3, 0),//修改label相对于maker的位置
                    content: String.fromCharCode(65+i)
                });
                marker.content = poiArr[i].name+"<br/>"+poiArr[i].address;
                marker.on('click', markerClick);
//                    marker.emit('click', {target:marker});
                markers.push(marker);

                str+='<li>';
                str+='<div class="res-data">';
                str+='<div class="left res-marker">';
                str+='<span>'+String.fromCharCode(65+i)+'</span>';
                str+='</div>';
                str+='<div class="left res-address">';
                str+='<div class="title">'+poiArr[i].name+'</div>';
                str+='<div>地址：<span class="rout">'+poiArr[i].address+'</span></div>';
                str+='<div>坐标：<span class="point">'+poiArr[i].location.lng+","+poiArr[i].location.lat+'</span></div>';
                str+='</div>';
                str+='<div class="clearfix"></div>';
                str+='</div>';
                str+='</li>';
            }
            str+='</ul>';
            $("#result").html(str);
            $("#s-point").text(poiArr[0].location.lng+","+poiArr[0].location.lat);
            //设置地图显示级别及中心点
            map.setZoomAndCenter(14,new AMap.LngLat(poiArr[0].location.lng,poiArr[0].location.lat));
            //获取查询城市信息
            map.getCity(function(res){
                $("#s-city").text(res.province+res.city);
            });

        }
    });
}

//点击标注  显示信息窗口及内容
function markerClick(e) {
    infoWindow.setContent(e.target.content);
    infoWindow.open(map, e.target.getPosition());
}