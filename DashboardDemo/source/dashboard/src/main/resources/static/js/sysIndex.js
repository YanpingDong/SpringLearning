    $(function () {
       // $('#container').load("/html/welcome.html");
        $('ul.sidebar-menu li').click(function () {
            var li = $('ul.sidebar-menu li.active');
            li.removeClass('active');
            $(this).addClass('active');
        });

        $('.myLeftMenu').click(function (e) {
//            var url = $(this).attr('data');
            e.preventDefault();
            var url=$(this).attr('href');
            console.log(url);
            $('#container').load(url);

        });
    })