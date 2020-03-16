var ws = function () {
    var socket;
    var messageDto = {
        fromId: null,
        toId: null,
        message: null
    };
    function openSocket(socketUrl) {
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else{
            console.log("开启WebSocket");
            socketUrl=socketUrl.replace("https","wss").replace("http","ws");
            if(socket!=null){
                socket.close();
                socket=null;
            }
            socket = new WebSocket(socketUrl);
            //打开事件
            socket.onopen = function() {
                console.log("websocket已打开");
                //socket.send("这是来自客户端的消息" + location.href + new Date());
            };
            //获得消息事件
            socket.onmessage = function(msg) {
                console.log(msg.data);
                //发现消息进入    开始处理前端触发逻辑
            };
            //关闭事件
            socket.onclose = function() {
                console.log("websocket已关闭");
            };
            //发生了错误事件
            socket.onerror = function() {
                console.log("websocket发生了错误");
            }
        }
    }
    function sendMessage() {
        if(typeof(WebSocket) == "undefined") {
            console.log("您的浏览器不支持WebSocket");
        }else {
            console.log("您的浏览器支持WebSocket");
            console.log(JSON.stringify(messageDto));
            socket.send(JSON.stringify(messageDto));
        }
    }
    return {
        openSocket: function (socketUrl) {
            openSocket(socketUrl);
        },
        sendMessage: function () {
            sendMessage()
        },
        messageDto: messageDto
    }
}();

