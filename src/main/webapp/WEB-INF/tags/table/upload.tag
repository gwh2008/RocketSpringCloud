<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"%>
<%@ attribute name="url" type="java.lang.String" required="true"%>
<button class="btn btn-white  btn-sm" data-toggle="tooltip" data-placement="left" onclick="upload()" title="上传模块"><i class="fa fa-file-text-o"></i> ${label==null?'上传模块':label}</button>

<sys:message content="${message}"/>
<%-- 使用方法： 1.将本tag写在查询的form之前；2.传入table的id和controller的url --%>
<script type="text/javascript">
    $(document).ready(function() {
        $('#${id} thead tr th input.i-checks').on('ifChecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
            $('#${id} tbody tr td input.i-checks').iCheck('check');
        });

        $('#${id} thead tr th input.i-checks').on('ifUnchecked', function(event){ //ifCreated 事件应该在插件初始化之前绑定
            $('#${id} tbody tr td input.i-checks').iCheck('uncheck');
        });

    });

    function upload(){

        // var url = $(this).attr('data-url');
        var str="";
        var ids="";
        var size = $("#${id} tbody tr td input.i-checks:checked").size();
        if(size == 0 ){
            top.layer.alert('请至少选择一条数据!', {icon: 0, title:'警告'});
            return;
        }

        if(size > 1 ){
            top.layer.alert('只能选择一条数据!', {icon: 0, title:'警告'});
            return;
        }
        var id =  $("#${id} tbody tr td input.i-checks:checkbox:checked").attr("id");
        var moduleName =  $("#${id} tbody tr td input.i-checks:checkbox:checked").attr("moduleName");

        top.layer.confirm('确认要上传模块吗? moduleName: '+moduleName, {icon: 3, title:'系统提示'}, function(index){
            //do something
            $.ajax({
                type : "POST",
                url : "${ctx}/gaowh/gaowh/upload",
                data : {"moduleName":moduleName},
                //dataType : "JSON",
//                success : function(data, textStatus, jqXHR) {
//                    top.layer.alert('上传功能模块成功!', {icon: 0, title:'提示'});
//                    top.layer.close(index);
//                }
                success : function(data, textStatus) {
                    top.layer.msg('模块上传成功！', {
                        icon: 1,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                    });
                }
            });
           top.layer.close(index);
           // layer.closeAll(index);
        });

    }

</script>