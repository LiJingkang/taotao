<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div>
	 <ul id="contentCategory" class="easyui-tree">
    </ul>
</div>
<%--// menuHandler 点击以后触发事件--%>
<div id="contentCategoryMenu" class="easyui-menu" style="width:120px;" data-options="onClick:menuHandler">
    <%--点击菜单以后出现。 在菜单上绑定事件--%>
    <div data-options="iconCls:'icon-add',name:'add'">添加</div>
    <div data-options="iconCls:'icon-remove',name:'rename'">重命名</div>
    <div class="menu-sep"></div>
    <div data-options="iconCls:'icon-remove',name:'delete'">删除</div>
</div>
<script type="text/javascript">
$(function(){
	$("#contentCategory").tree({
	    // 异步tree 先返回顶层结点
		url : '/content/category/list',
		animate: true,
		method : "GET",
        // on 一个事件 点右键事件
		onContextMenu: function(e,node){ // e 代表事件， node 代表点击的节点
            e.preventDefault(); // 事件处理，向下传递
            $(this).tree('select',node.target); // 转成jQurey对象 使用 tree的方法。 把当前结点变为选中状态
            $('#contentCategoryMenu').menu('show',{ // 显示，后面是坐标。 调用 contentCategoryMenu
                left: e.pageX,
                top: e.pageY
            });
        },
        // 当编辑结束的时候触发这个事件
        // 真正提交数据的
        onAfterEdit : function(node){
        	var _tree = $(this); // 拿到node
        	if(node.id == 0){
        		// 新增节点
                // 提交一个请求给后台
        		$.post("/content/category/create",{parentId:node.parentId,name:node.text},function(data){ // 请求以后拿到data返回数据
        			if(data.status == 200){ // 判断是否成功
        				_tree.tree("update",{ // 成功以后更新
            				target : node.target,
            				id : data.data.id
            			});
        			}else{
        				$.messager.alert('提示','创建'+node.text+' 分类失败!');
        			}
        		});
        	}else{
        		$.post("/content/category/update",{id:node.id,name:node.text});
        	}
        }
	});
});
function menuHandler(item){ // item 点击了拿一个项
	var tree = $("#contentCategory"); // 拿到 tree
	var node = tree.tree("getSelected"); // 取到选中的项
	if(item.name === "add"){ // 判断选到的是不是 add
		tree.tree('append', { // 添加子节点
            parent: (node?node.target:null), // 拿到 garget
            data: [{ // 结点的内容
                text: '新建分类',  // 三个属性
                id : 0,
                parentId : node.id
            }]
        }); 
		var _node = tree.tree('find',0); // 在tree里面找id 为0 的结点
		tree.tree("select",_node.target).tree('beginEdit',_node.target); // 选中当前结点，变为可编辑状态
	}else if(item.name === "rename"){
		tree.tree('beginEdit',node.target);
	}else if(item.name === "delete"){
		$.messager.confirm('确认','确定删除名为 '+node.text+' 的分类吗？',function(r){
			if(r){
				$.post("/content/category/delete/",{id:node.id},function(){
					tree.tree("remove",node.target);
				});	
			}
		});
	}
}
</script>