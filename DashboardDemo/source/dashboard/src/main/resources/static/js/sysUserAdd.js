$(document).ready(function () {

  $('button[name="submit"]').click(function(e){
    //构造一个表单，FormData是HTML5新增的
    var form = new FormData();
    var permissionId = $('input[name="permissionId"]').val();
    var permissionName = $('input[name="permissionName"]').val();
    var permission = $('input[name="permission"]').val();
    var url = $('input[name="url"]').val();
    var available = $('input[name="available"]').val();

    form.append("permissionId", permissionId);
    form.append("permissionName", permissionName);
    form.append("permission", permission);
    form.append("url", url);
    form.append("available", available);

    $.ajax({
        async: false,
        type: "put",
        url:'/permission/'+ permissionId,
        data: form,
        async: true,        //异步
        processData: false,  //很重要，告诉jquery不要对form进行处理
        contentType: false,  //很重要，指定为false才能形成正确的Content-Type
        success: function () {
          },
        error: function () {
        }
    })
  });

  $('button[name="backToList"]').click(function (e) {
      var url="/user/ulist";
      $('#container').load(url);
   });

  $("a[name='home']").click(function(){
    var url = "/home/prompt";
    $('#container').load(url);
  });

});