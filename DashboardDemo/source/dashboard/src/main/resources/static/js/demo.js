$(function () {
    //Add text editor
    $('#compose-textarea').summernote()

    $('button[name="draft"]').click(function (e) {
        var summernote = $('#compose-textarea').summernote('code');
        var title = $('#compose-title').val();
        var creator = $('#compose-creator').val();
        console.log(summernote);
        console.log(title);
        console.log(creator);

        var opt={"title":title,"createBy":creator,"content":summernote};
        console.log(opt);
        $.ajax({
          type: "POST",
          url: "/cms",
          dataType : "json",
          contentType : "application/json",      //网上很多介绍加上此参数的，后来我发现不加入这个参数才会请求成功。
          data: JSON.stringify(opt),
          success: function (d) {
            console.log(d);
          }
        });
    });
  })