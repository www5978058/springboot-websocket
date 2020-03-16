var app = {
  init: function () {
      console.log("init")
  },
    name: 'zhangsan'
};
var app1 = function(){
    var stuNum = 10;
    var init = function (num) {
        console.log("app1 init "+num);
    }
    return {
        stuNum: stuNum,
        init: function (num) {
            init(num);
        }
    }
}();//小括号代表改函数直接执行，return 可以通过app1. 调用内部的属性和函数
$(function () {
    console.log(app1.stuNum);
    app1.init(11);
});
