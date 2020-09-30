function getTree() {
  var tree = null;
  $.ajax({
    type: "get",
    url: "/permission/all",
    async: false,
    dataType: 'json',
    success: function (data) {
        tree = data;
    }
  });
  return tree;
}

function show_create_permission_page()
{
    add(0);
}

$(function() {

    var $table = $('#table');

    $table.bootstrapTable({
        data:getTree(),
        idField: 'id',
        dataType:'jsonp',
        columns: [
            { field: 'check',  checkbox: true, formatter: function (value, row, index) {
                    if (row.check == true) {
                        // console.log(row.serverName);
                        //设置选中
                        return {  checked: true };
                    }
                }
            },
            { field: 'name',  title: '名称' },
            { field: 'status',  title: '状态', sortable: true,  align: 'center', formatter: 'statusFormatter'  },
            { field: 'permissionValue', title: '权限值'  },
            { field: 'url', title: 'url'  },
            { field: 'operate', title: '操作', align: 'center', events: operateEvents, formatter: 'operateFormatter' },
        ],

        // bootstrap-table-treegrid.js 插件配置 -- start
        //在哪一列展开树形
        treeShowField: 'name',
        //指定父id列
        parentIdField: 'pid',
        onResetView: function(data) {
            $table.treegrid({
                //initialState: 'collapsed',// 所有节点都折叠
                initialState: 'expanded',// 所有节点都展开，默认展开
                treeColumn: 1,
                // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function() {

                }
            });

            //只展开树形的第一级节点
            $table.treegrid('getRootNodes').treegrid('expand');

        },
        onCheck:function(row){
            var datas = $table.bootstrapTable('getData');
            // 勾选子类
            selectChilds(datas,row,"id","pid",true);

            // 勾选父类
            selectParentChecked(datas,row,"id","pid")

            // 刷新数据
            $table.bootstrapTable('load', datas);
        },

        onUncheck:function(row){
            var datas = $table.bootstrapTable('getData');
            selectChilds(datas,row,"id","pid",false);
            $table.bootstrapTable('load', datas);
        },
        // bootstrap-table-treetreegrid.js 插件配置 -- end
    });

    function test() {
        var selRows = $table.bootstrapTable("getSelections");
        console.log(selRows);
        if(selRows.length == 0){
            alert("请至少选择一行");
            return;
        }
        var postData = "";
        $.each(selRows,function(i) {
            postData +=  this.id;
            if (i < selRows.length - 1) {
                postData += "， ";
            }
        });
        alert("你选中行的 id 为："+postData);
    }

    $("#test").click(function(){
        test();
    });
});

// 格式化按钮
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfadd btn btn-info btn-sm" style="margin-right:15px;"><i class="fa fa-plus" ></i>&nbsp;新增</button>',
        '<button type="button" class="RoleOfedit btn btn-primary btn-sm" style="margin-right:15px;"><i class="fas fa-pencil-alt"> </i>&nbsp;修改</button>',
        '<button type="button" class="RoleOfdelete btn btn-danger btn-sm" style="margin-right:15px;"><i class="fas fa-trash"></i>&nbsp;删除</button>'
    ].join('');

}
// 格式化类型
function typeFormatter(value, row, index) {
    if (value === 'menu') {  return '菜单';  }
    if (value === 'button') {  return '按钮'; }
    if (value === 'api') {  return '接口'; }
    return '-';
}
// 格式化状态
function statusFormatter(value, row, index) {
    if (value === 1) {
        return '<span class="label label-success">正常</span>';
    } else {
        return '<span class="label label-default">锁定</span>';
    }
}

//初始化操作按钮的方法
window.operateEvents = {
    'click .RoleOfadd': function (e, value, row, index) {
        add(row.id);
    },
    'click .RoleOfdelete': function (e, value, row, index) {
        del(row.id);
    },
    'click .RoleOfedit': function (e, value, row, index) {
        update(row.id);
    }
};
/**
 * 选中父项时，同时选中子项
 * @param datas 所有的数据
 * @param row 当前数据
 * @param id id 字段名
 * @param pid 父id字段名
 */
function selectChilds(datas,row,id,pid,checked) {
    for(var i in datas){
        if(datas[i][pid] == row[id]){
            datas[i].check=checked;
            selectChilds(datas,datas[i],id,pid,checked);
        };
    }
}

function selectParentChecked(datas,row,id,pid){
    console.log(datas);
    for(var i in datas){
        if(datas[i][id] == row[pid]){
            datas[i].check=true;
            selectParentChecked(datas,datas[i],id,pid);
        };
    }
}



function add(id) {
    alert("add 方法 , id = " + id);
    var addUrl="permission/" + id
    $('#container').load(addUrl);
}

function del(id) {
    $.ajax({
        type: "delete",
        url: "/permission/"+id,
        async: false,
        dataType: 'json',
        success: function (data) {
            if(data!=0)
            {
                $('#container').load(data.url);
            }
            else
            {
                alert(data.msg);
            }
        }
      });
    alert("del 方法 , id = " + id);
}

function update(id) {
    var addUrl="permission/" + id + "/update"
    $('#container').load(addUrl);
}