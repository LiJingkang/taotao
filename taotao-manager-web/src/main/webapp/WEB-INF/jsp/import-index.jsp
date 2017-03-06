<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
    <%-- 创建一个a 标签，给予样式clss，绑定事件 importIndex --%>
    <a class="easyui-linkbutton" onclick="importIndex()">一键导入商品数据到索引库</a>
</div>
<%-- 导入内嵌的js --%>
<script type="text/javascript">
function importIndex(){
    // url ，参数，返回值
    $.post("/index/import",null,function(data){
        if(data.status == 200){
            $.messager.alert("提示","导入索引库成功！！！！");
        } else {
            $.messager.alert("提示","导入索引库失败~~~~~~~");
        }
    });
}
</script>