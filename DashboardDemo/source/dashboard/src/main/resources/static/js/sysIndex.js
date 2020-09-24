    $(function () {
        $('#container').load("/home/prompt");
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

        $.ajax({
                  type: "GET",
                  url: "/log/getLogList1",
                  dataType : "json",
                  contentType : "application/json",
                  success: function (d) {
                    console.log(d['data']);
                    for(subData  in d['data']){
                      for(detail in d['data'][subData])
                      {
                      let $menu = $('<a>',{
                         'data-widget':"treeview",
                         'data-accordion':"false",
                         role:"menu",
                         'class':"nav nav-pills nav-sidebar flex-column-1"
                      });
//                      let $menu = $("<ul data-widget=\"treeview\" data-accordion=\"false\" role=\"menu\" class=\"nav nav-pills nav-sidebar flex-column-1\"></>");

                      let $menu_key = $('<a />',{
                          href: "#",
                          'class': "nav-link"
                      });
                      let menuename = d['data'][subData][detail]
                      let $menue_p = $('<p />').append( menuename + "<i class=\"right fas fa-angle-left\"></i>")
                         let abc = $menu_key.append($menue_p);
                         let cba = $menu.append(abc);
                         $("#dyptest").append(cba);
                         console.log(d['data'][subData][detail]);
                      }
                    }
                  }
                });
    })