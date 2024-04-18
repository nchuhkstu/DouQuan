<template>
    <div id="live">
        <div id="live-left">
            <div id="live-left-head">
                <div id="live-left-head-left">
                    <img id="live-information-avatar" src="../../images/sys/love.png">
                </div>
                <div id="live-left-head-right">
                    <div id="live-information-top">
                        <label id="live-title">{{ liveInformation[currentLive-1].title }}</label>
                    </div>
                    <div id="live-information-bottom">
                        <label id="live-name">{{ liveInformation[currentLive-1].name }}</label>
                        <label class="information">在线人数：{{ peopleNum }}</label>
                        <label class="information">直播热度：{{ hot }}</label>
                        <label class="information">已开播：{{ time }}</label>
                    </div>
                </div>
            </div>
            <div id="live-left-body">
                <video id="live-video" controls></video>
            </div>
            <div id="live-left-foot"></div>
        </div>
        <div id="live-right">
            <div id="live-right-top">
                <liveCommet v-for="liveComment in liveComments" :name="liveComment.name" :content="liveComment.content"></liveCommet>
            </div>
            <div id="live-right-bottom">
                <input id="live-input" v-model="content" autocomplete="off">
                <button id="live-submit" @click="sendMessage">发送</button>
            </div>
        </div>
        <login ref="loginRef"></login>
    </div>
</template>
<script>
import Hls from 'hls.js';
import { io } from 'socket.io-client';
import liveCommet from './liveCommet.vue';
import login from './login.vue';
import { islogin,getDetailByUserid} from '../api/user';
export default{
    name:'live',
    components:{
        liveCommet,
        login,
    },
    data(){
        return{
            title:'12312',
            peopleNum:null,
            hot:null,
            name:'laoliu',
            avatar:null,
            time:null,
            liveInformation:[
                {title:'直播间1标题',name:'主播1号',src:'http://127.0.0.1:2000/live/.m3u8'},
                {title:'直播间2标题',name:'主播2号',src:'http://127.0.0.1:2000/live/.m3u8'}
            ],
            socketio:io(`${import.meta.env.VITE_BASE_API}/room1`),
            content:null,
            liveComments:[],
            heartbeatTimer:null,
            currentLive:1,
        }
    },
    methods:{
        sendMessage(){
            islogin()
            .then(response=>{
                const userid = response.data.data;
                if(!userid){
                    this.$refs.loginRef.modalStatus=true;
                    return;
                }
                getDetailByUserid({userid:userid})
                .then(response=>{
                   const name = response.data.data.user.name;
                   this.socketio.emit('message',{name:name,content:this.content});
                   this.content='';
                })
            })
        },
        reciveMessage(){
            this.socketio.on('message',(data)=>{
                this.liveComments.push({name:data.name,content:data.content});
            })
        },
        join(){
            islogin()
            .then(response=>{
                const userid = response.data.data;
                if(!userid){
                    return;
                }
                getDetailByUserid({userid:userid})
                .then(response=>{
                    const name = response.data.data.user.name;
                    this.socketio.emit('join',name);
                    this.heartbeatTimer = setInterval(() => {
                        this.socketio.emit('heartbeat',name);
                    }, 4000); // 每5秒发送一次心跳请求
                })
            })
        },
        reciveJoin(){
            this.socketio.on('join',(data)=>{
                this.liveComments.push({name:data,content:'进入直播间'});
            })
        },
        leave(){
            islogin()
            .then(response=>{
                const userid = response.data.data;
                if(!userid){
                    return;
                }
                getDetailByUserid({userid:userid})
                .then(response=>{
                   const name = response.data.data.user.name;
                   this.socketio.emit('leave',name);
                   this.heartbeatTimer=null;
                })
            })
        },
        reciveLeave(){
            this.socketio.on('leave',(data)=>{
                this.liveComments.push({name:data,content:'离开直播间'});
            })
        },
        recivePeopleNum(){
            this.socketio.on('peopleNum',(data)=>{
                this.peopleNum = data;
            })
        },
    },
    mounted(){
        this.join();
        this.reciveJoin();
        this.reciveMessage();
        this.reciveLeave();
        this.recivePeopleNum();
        // window.addEventListener('beforeunload',this.leave);
        var hls = new Hls();
        hls.loadSource(`${import.meta.env.VITE_BASE_LIVE}/live/.m3u8`);
        const video = document.getElementById('live-video');
        hls.attachMedia(video);
    },
    beforeUnmount(){
        clearInterval(this.heartbeatTimer);
        this.heartbeatTimer=null;
        this.socketio.off();
    }
}
</script>
<style>
#live{
    background-color: rgb(17, 17, 17);
    width: 100%;
    height: 100%;
    min-width: 1000px;
    display: flex;
    justify-content: center;
    align-items: center;
}
#live-left{
    height: 90%;
    width: 60%;
    min-width: 600px;
    /* margin-top: 5%; */
    background-color: rgb(35,35,35);
    border-top-left-radius: 20px;
    border-bottom-left-radius: 20px;
    /* background-color: yellow; */
}
#live-left-head{
    width: 100%;
    height: 10%;
    display: flex;
}
#live-left-head-left{
    height: 100%;
}
#live-information-avatar{
    height: 100%
}
#live-information-top{
    font-size: 20px;
    height: 60%;
}
#live-information-bottom{
    height: 40%;
}
#live-left-body{
    width: 100%;
    height: 80%;
    /* background-color: brown; */
}
#live-video{
    width: 100%;
    height: 100%;
}
#live-left-foot{
    width: 100%;
    height: 10%;
}
#live-right{
    height: 90%;
    width: 20%;
    min-width: 200px;
    /* margin-top: 5%; */
    background-color:rgb(35,35,35);
    border-top-right-radius: 20px;
    border-bottom-right-radius: 20px;
}
.information{
    margin-left: 30px;
    color: rgb(191,191,191);
}
#live-right-top{
    height: 90%;
    width: 100%;
}
#live-right-bottom{
    height: 10%;
    width: 100%;
}
#live-input{
    height: 100%;
    width: 80%;
    border: 0px;
    padding: 0px;
    outline: none;
    /* background-color: rgb(60,60,60); */
    font-size: 17px;
    color: rgb(191,191,191);
}
#live-submit{
    border: 0px;
    padding: 0px;
    height: 100%;
    width: 20%;
}
#live-title{
    color: rgb(191,191,191);
}
#live-name{
    color: #ff8200;
}
</style>