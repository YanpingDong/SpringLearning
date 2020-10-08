$(document).ready(function () {

  $('button[name="submit"]').click(function(e){
    //构造一个表单，FormData是HTML5新增的
    var form = new FormData();
    var name = $('input[name="name"]').val();
    var tel = $('input[name="tel"]').val();
    var email = $('input[name="email"]').val();
    var state = $('input[name="state"]').val();
    var password = $('input[name="password"]').val();
    form.append("name", name);
    form.append("tel", tel);
    form.append("email", email);
    form.append("state", state);
    form.append("password", password);
    $.ajax({
        async: false,
        type: "post",
        url:'/user',
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