$(function () {
    //Add text editor
    $('#compose-textarea').summernote(
        {
            lang: 'zh-CN',
            callbacks:{
                onImageUpload: function(files){
                    //上传图片到服务器
                    var formData = new FormData();
                    formData.append('image',files[0]);
                    $.ajax({
                    url : '/image',//后台文件上传接口
                    type : 'POST',
                    data : formData,
                    processData : false,
                    contentType : false,
                    success : function(imageId) {
                        var path='/image/'+imageId
                        $('#compose-textarea').summernote('insertImage',path);
                    },
                    error:function(){
                        alert("上传失败");
                    }
                    });
                }
            }
        }
    )

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