<template>
    <div id="video-container">
        <div id="video-head">
            <label @click="goVideoPublish">发布</label>
        </div>
        <div id="video-body">
            <div id="video-left">
                
            </div>
            <div id="video-mid">
                <div id="video-player-container">
                    <video id="video-player" autoplay="true" @click="pauseOrStart" loop>
                        
                    </video>
                    <img id="player-status-2" @click="pauseOrStart" src="../../images/sys/pause.png">
                    <div id="video-information">
                        <div id="video-information-head">
                            <div id="video-author-name" v-if="videos[currentVideo - 1]">{{ '@'+videos[currentVideo-1].name }}</div>
                            <div id="video-time" v-if="videos[currentVideo-1]">{{ videos[currentVideo-1].publishTime }}</div>
                        </div>
                        <div id="video-title" v-if="videos[currentVideo - 1]">{{ videos[currentVideo-1].title }}</div>
                    </div>
                    <div id="video-player-controller">
                        <Progressbar id="progressbar" :value="progress"></Progressbar>
                        <div id="video-player-controller-body">
                            <img id="player-status" src="../../images/sys/play.png" @click="pauseOrStart">
                            <label id="player-time" v-if="videos[currentVideo - 1]" >{{ formattedTime(videos[this.currentVideo-1].currenttime)+'/'+formattedTime(videos[this.currentVideo-1].totaltime) }}</label>
                            <div id="player-continue" @click="setContinue">
                                <div id="player-continue-button"><statusButton :id="1" :initStatus="true" ref="statusButtonRef"></statusButton></div>
                                <label id="player-continue-label">连播</label>
                            </div>
                            <div id="player-clear" @click="hideInformation">
                                <div id="player-clear-button"><statusButton :id="2" :initStatus="false" ref="statusButtonRef2"></statusButton></div>
                                <label id="player-clear-label" @click="hideInformation">清屏</label>
                            </div>
                            
                            <div id="player-resolution-container">
                                <div id="player-resolution">清晰</div>
                                <div id="player-resolution-options">
                                    <input class="player-resolution-option" type="button" @click="getResolution('high')" value="高清">
                                    <input class="player-resolution-option selected" type="button" @click="getResolution('intermediate')" value="清晰">
                                    <input class="player-resolution-option" type="button" @click="getResolution('mid')" value="流畅">
                                    <input class="player-resolution-option" type="button" @click="getResolution('low')" value="极速">
                                </div>
                            </div>
                            <div id="player-speed-container">
                                <div id="player-speed">倍速</div>
                                <div id="player-speed-options">
                                    <input class="player-speed-option" type="button" @click="getSpeed(0.75)" value="0.75x">
                                    <input class="player-speed-option selected" type="button" @click="getSpeed(1.0)" value="1.0x">
                                    <input class="player-speed-option" type="button" @click="getSpeed(1.25)" value="1.25x">
                                    <input class="player-speed-option" type="button" @click="getSpeed(1.5)" value="1.5x">
                                    <input class="player-speed-option" type="button" @click="getSpeed(2.0)" value="2.0x">
                                </div>
                            </div>
                            <div id="player-sound-container">
                                <img id="player-sound" src="../../images/sys/sound.png" @click="setSoundZero">
                                <div id="player-sound-inputs">
                                    <input id="player-sound-input" type="range" v-model="sound" min="0" max="1" step="0.01" @input="setSound"/>
                                    <label id="player-sound-display">{{ formattedSound(sound) }}</label>
                                </div>
                                
                            </div>
                            <img id="player-full" src="../../images/sys/fullscreen.png" @click="fullScreen">
                        </div>
                    </div>
                    <div id="video-player-right">
                        <img id="player-avatar"  v-if="videos[currentVideo - 1]" :src="videos[currentVideo-1].avatarsrc">
                        <img id="player-like" src="../../images/sys/love.png" @click="like()">
                        <div id="player-like-num" v-if="videos[currentVideo - 1]">{{ videos[currentVideo - 1].likeNum }}</div>
                        <img id="player-comment" src="../../images/sys/comment1.png" @click="openCommentArea">
                        <div id="player-comment-num" v-if="videos[currentVideo - 1]">{{ videos[currentVideo-1].commentNum }}</div>
                    </div>
                </div>
            </div>
            <div id="video-right">
                <div id="video-right-head"><label>TA的作品</label><label style="border-bottom: 3px solid rgb(255,44,85);">评论</label><label></label><label></label></div>
                <div id="video-right-body" ><CommentArea :comments="comments" @comment="handleComment" :videoId="videos[currentVideo-1].videoid" v-if="videos[currentVideo-1]" :commentNum="videos[currentVideo-1].commentNum"></CommentArea></div>
            </div>
            <div id="video-right-right">
                      
            </div>
        </div>
        <login ref="loginRef"></login>
        
    </div>
</template>
<script>
import router from '../modules/router';
import Hls from 'hls.js'
import { findVideoByPage,likeVideo,dislikeVideo,islikeVideo} from '../api/video'
import { searchingVideo } from '../api/search';
import { getDetailByUserid,islogin } from '../api/user';
import { getAvatar } from '../api/image';
import { getcommentVideo,getcommentNumVideo } from '../api/commentVideo';

import CommentArea from '../components/commentArea.vue';
import VideoNavigation from '../components/videoNavigation.vue';
import Progressbar from '../components/progressbar.vue';
import login from './login.vue';
import statusButton from '../components/statusButton.vue';
export default{
    name:'videoCard',
    
    data(){
        return{
            userid:'',
            pageSize:10,
            currentPage:1,
            totoalPage:'',
            videos:[],
            currentVideo:1,
            totalVideo:'',
            playerStatus:true,
            sound:1.0,
            speed:null,
            resolution:null,
            lastSound:1.0,
            quietStatus:false,
            comments:[],
            commentAreaStatus:false,
            isScrolling:false,
            informationStatus:true,
            continue:true,
        }
    },
    components:{
        CommentArea,
        VideoNavigation,
        Progressbar,
        login,
        statusButton,
    },
    computed: {
        progress() {
            if(this.videos[this.currentVideo-1]!=null){
                if (this.videos[this.currentVideo-1].totaltime === 0) {
                return 0; // 避免除以零错误
                }
                return (this.videos[this.currentVideo-1].currenttime / this.videos[this.currentVideo-1].totaltime) * 100;
            
            }
        },
    },
    methods:{
        islogin(){
            islogin().then(response=>{
                if(response.data.data!=null){
                    this.userid = response.data.data;
                }
            })
        },
        async handleComment(data){
            const avatarsrc = await this.getAvatar(this.userid.toString());
            let name = null;
            await getDetailByUserid({userid:this.userid.toString()}).then(response=>{
                name = response.data.data.user.name;
            })
            const content = data;
            const publishTime = new Date();
            const year = (publishTime.getFullYear() === new Date().getFullYear()) ? "" : publishTime.getFullYear() + "/";
            const month = (publishTime.getMonth() + 1).toString().padStart(2, "0");
            const day = publishTime.getDate().toString().padStart(2, "0");
            const hour = publishTime.getHours().toString().padStart(2, "0");
            const minute = publishTime.getMinutes().toString().padStart(2, "0");
            const time2 = `${year}${month}/${day} ${hour}:${minute}`;
            this.comments.push({
                avatarsrc:avatarsrc,
                name:name,
                content:content,
                time:time2,
            });
            this.videos[this.currentVideo-1].commentNum++;
        },
        formattedTime(time) {
            const minutes = Math.floor(time / 60);
            const seconds = Math.floor(time % 60);
            const formattedMinutes = String(minutes).padStart(2, '0');
            const formattedSeconds = String(seconds).padStart(2, '0');
            
            return `${formattedMinutes}:${formattedSeconds}`;
        },
        formattedSound(sound){
            return (sound * 100).toFixed(0);
        },
        formattedSpeed(speed){
            if(speed === 1){
                return '1.0';
            }
            if(speed === 2){
                return '2.0';
            }
            else{
                return speed;
            }
        },
        formattedResolution(resolution){
            if(resolution == 'high'){
                return '高清';
            }
            if(resolution == 'intermediate'){
                return '清晰';
            }
            if(resolution == 'mid'){
                return '流畅';
            }
            if(resolution == 'low'){
                return '极速';
            }
        },
        goVideoPublish(){
            if(!this.userid){
                this.$refs.loginRef.modalStatus=true;
            }
            else{
                router.push('/videoPublish');
            }
        },
        //监听滑轨
        async handleFindVideoByPage(){
            const response = await searchingVideo(this.pageSize, this.currentPage);
            this.totalVideo = response.data.data.total;
            this.totoalPage = Math.floor(response.data.data.total / this.pageSize) + 1;
            for (let i = 0; i < response.data.data.total - this.pageSize * (response.data.data.currentPage - 1) && i < this.pageSize; i++) {
                try {
                    const response1 = await getDetailByUserid({ userid: response.data.data.data[i].userid.toString() });
                    const avatarsrc = await this.getAvatar(response.data.data.data[i].userid.toString());
                    const publishTime = new Date(response.data.data.data[i].publishtime);
                    const year = (publishTime.getUTCFullYear() === new Date().getUTCFullYear()) ? "" : publishTime.getUTCFullYear() + "/";
                    const month = (publishTime.getUTCMonth() + 1).toString().padStart(2, "0");
                    const day = publishTime.getUTCDate().toString().padStart(2, "0");
                    const hour = publishTime.getUTCHours().toString().padStart(2, "0");
                    const minute = publishTime.getUTCMinutes().toString().padStart(2, "0");
                    const time = `${year}${month}/${day} ${hour}:${minute}`;
                    const totaltime2 = response.data.data.data[i].totaltime;
                    const [hours, minutes, seconds] = totaltime2.split(':');
                    const totaltime = parseInt(hours) * 3600 + parseInt(minutes) * 60 + parseInt(seconds);
                    const s = response.data.data.data[i].likelist;
                    const a = s.split(",");
                    let likeNum = a.length;
                    if (s.length == 0) {
                        likeNum--;
                    }
                    const videoid = response.data.data.data[i].videoid.toString();
                    const videosrc = `http://127.0.0.1:10/hls/${videoid}/intermediate/index.m3u8`;
                    const commentNum = (await getcommentNumVideo(response.data.data.data[i].videoid)).data.data;
                    let likeStatus = false;
                    let likeStatus_response = await islikeVideo({videoid:videoid})
                    if(likeStatus_response.data.code==509){
                        likeStatus = true;
                    }
                    this.videos.push({ 
                        avatarsrc: avatarsrc, 
                        name: response1.data.data.user.name, 
                        publishTime: time, 
                        title: response.data.data.data[i].title, 
                        videoid:videoid,
                        likeNum:likeNum,
                        commentNum:commentNum,
                        currenttime:0,
                        totaltime:totaltime,
                        videosrc:videosrc,
                        likeStatus:likeStatus,
                    });
                } catch (error) {
                    console.error(error);
                }
            }
            this.updateLikeStatus();
            var hls = new Hls();
            hls.loadSource(this.videos[this.currentVideo-1].videosrc);
            // hls.loadSource('http://127.0.0.1:10/hls/54/intermediate/index.m3u8');
            const video = document.getElementById('video-player');
            hls.attachMedia(video);
        },
        openCommentArea(){
            if(this.commentAreaStatus==false){
                document.getElementById('video-right').style.display='block';
                document.getElementById('video-mid').style.width='55%';
                document.getElementById('video-mid').style.borderTopRightRadius='0px';
                document.getElementById('video-mid').style.borderBottomRightRadius='0px';
                this.commentAreaStatus=true;
                this.getComment();
            }
            else{
                document.getElementById('video-right').style.display='none';
                document.getElementById('video-mid').style.width='85%';
                document.getElementById('video-mid').style.borderTopRightRadius='15px';
                document.getElementById('video-mid').style.borderBottomRightRadius='15px';
                this.commentAreaStatus=false;
            }
        },
        getComment(){
            this.comments=[];
            getcommentVideo(this.videos[this.currentVideo-1].videoid)
            .then(async response=>{
                for(let i=0;i<response.data.data.length;i++){
                    const avatarsrc = await this.getAvatar(response.data.data[i].userid.toString());
                    let name = null;
                    await getDetailByUserid({userid:response.data.data[i].userid.toString()}).then(response=>{
                        name = response.data.data.user.name;
                    })
                    const content = response.data.data[i].content;
                    const time = response.data.data[i].commenttime;
                    const publishTime = new Date(time);
                    const year = (publishTime.getUTCFullYear() === new Date().getUTCFullYear()) ? "" : publishTime.getUTCFullYear() + "/";
                    const month = (publishTime.getUTCMonth() + 1).toString().padStart(2, "0");
                    const day = publishTime.getUTCDate().toString().padStart(2, "0");
                    const hour = publishTime.getUTCHours().toString().padStart(2, "0");
                    const minute = publishTime.getUTCMinutes().toString().padStart(2, "0");
                    const time2 = `${year}${month}/${day} ${hour}:${minute}`;
                    const commentid = response.data.data[i].commentid.toString();
                    this.comments.push({
                        avatarsrc:avatarsrc,
                        name:name,
                        content:content,
                        time:time2,
                        commentid:commentid
                    });
                }
            })
        },
        getAvatar(userid) {
            return new Promise((resolve, reject) => {
                getAvatar({ userid: userid })
                .then(response => {
                    const blob = new Blob([response.data], { type: 'image/jpeg' });
                    const imageUrl = URL.createObjectURL(blob);
                    resolve(imageUrl);
                })
                .catch(error => {
                    console.error(error);
                    reject(error);
                });
            });
        },
        nextVideo(){
            if(this.currentVideo<this.totalVideo){
                this.currentVideo++;
                if(this.videos.length>=this.currentVideo){
                    this.setResolution();
                    this.setSrc();
                    this.getComment();
                    this.start();
                    this.updateLikeStatus();
                    this.setSpeed();
                }
            }
            else{
                alert("最后一条视频了");
            }
        },
        lastVideo(){
            if(this.currentVideo>1){
                this.currentVideo--;
                this.setResolution();
                this.setSrc();
                this.getComment();
                this.updateLikeStatus();
                this.setSpeed();
                this.start();
            }
        },
        fullScreen() {
            const videoPlayer = document.getElementById("video-player");
            if (document.fullscreenElement) {
                // 如果当前已处于全屏状态，则退出全屏
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                } 
            } 
            else {
                // 否则，进入全屏
                if (videoPlayer.requestFullscreen) {
                    videoPlayer.requestFullscreen();
                    videoPlayer.controls = false;
                } 
            }
        },
        //点赞
        like(){
            if(!this.userid){
                this.$refs.loginRef.modalStatus=true;
                return;
            }
            if(this.videos[this.currentVideo-1].likeStatus == false){
                likeVideo({videoid:this.videos[this.currentVideo-1].videoid})
                .then(response=>{
                    if(response.data.code!=509){
                        this.videos[this.currentVideo-1].likeNum++;
                        this.videos[this.currentVideo-1].likeStatus = true;
                        this.updateLikeStatus();
                    }
                })
            }
            else{
                dislikeVideo({userid:this.userid,videoid:this.videos[this.currentVideo-1].videoid})
                .then(response=>{
                    this.videos[this.currentVideo-1].likeNum--;
                    this.videos[this.currentVideo-1].likeStatus = false;
                    this.updateLikeStatus();
                })
            } 
        },
        updateLikeStatus(){
            let imgbutton = document.getElementById('player-like');
            if(this.videos[this.currentVideo-1].likeStatus==true){
                imgbutton.src = "../../images/sys/heart.png";
            }
            else{
                imgbutton.src = "../../images/sys/love.png";
            }
        },
        pauseOrStart(){
            const video = document.getElementById("video-player");
            const button = document.getElementById("player-status");
            const button2 = document.getElementById("player-status-2");
            if(this.playerStatus==true){
                this.playerStatus=false;
                video.pause();
                button.src = "../../images/sys/pause.png";
                button2.style.display = 'block'
            }
            else{
                this.playerStatus=true;
                video.play();
                button.src="../../images/sys/play.png";
                button2.style.display = 'none'
            }
        },
        // 只是设置按钮和状态，并不是靠这个来播放
        start(){
            this.playerStatus=true;
            const button = document.getElementById("player-status");
            const button2 = document.getElementById("player-status-2");
            button.src="../../images/sys/play.png";
            button2.style.display = 'none'
        },
        updateCurrentTime() {
            const video = document.getElementById("video-player");
            this.videos[this.currentVideo-1].currenttime = video.currentTime;
            if(this.continue==true){
                if(Math.abs(this.videos[this.currentVideo-1].totaltime-this.videos[this.currentVideo-1].currenttime)<0.5){
                    this.nextVideo();
                }
            }

        },
        listenTime(){
            const video = document.getElementById("video-player");
            video.addEventListener('timeupdate', this.updateCurrentTime);
        },
        notListenTime(){
            const video = document.getElementById("video-player");
            video.removeEventListener('timeupdate', this.updateCurrentTime);
        },
        updateProgressbar(event){
            const progressbar = document.getElementById('progressbar');
            const divWidth = progressbar.offsetWidth;
            const clickX = event.offsetX;
            const percentage = (clickX / divWidth) * 100;
            const video = document.getElementById("video-player");
            video.currentTime = this.videos[this.currentVideo-1].totaltime * percentage / 100;
        },
        listenProgressbar(){
            const progressbar = document.getElementById('progressbar');
            progressbar.addEventListener('click', this.updateProgressbar);
        },
        setSound(){
            const video = document.getElementById("video-player");
            video.volume = this.sound;
        },
        setSoundZero(){
            if(this.quietStatus==false){
                this.lastSound = this.sound;
                this.sound = 0;
                this.quietStatus = true;
            }
            else{
                this.sound = this.lastSound;
                this.quietStatus = false;
            }
        },
        setContinue(){
            this.$refs.statusButtonRef.updateButton();
            if(this.continue==true){
                this.continue=false;
            }
            else{
                this.continue=true;
            }
        },
        hideInformation(){
            this.$refs.statusButtonRef2.updateButton();
            const information = document.getElementById('video-information');
            if(this.informationStatus == true){
                this.informationStatus = false;
                information.style.display = 'none';
            }
            else{
                information.style.display = 'block';
                this.informationStatus = true;
            }
            
        },
        handleScroll(event) {
            if (this.isScrolling) {
                return;
            }
            this.isScrolling = true;
            const scrollDelta = event.deltaY;
            if (scrollDelta > 0) {
                this.nextVideo();
            }
            else {
                this.lastVideo();
            }
            setTimeout(() => {
                this.isScrolling = false;
            }, 1500); // 这里的延迟时间可以根据需要进行调整
        },
        getSpeed(speed){
            this.speed = speed;
            this.setSpeed();
        },
        setSpeed(){
            if(this.speed==null){
                return;
            }
            const video = document.getElementById('video-player');
            video.playbackRate = this.speed; // 设置倍速为 1.5 倍
            const playerspeed = document.getElementById('player-speed');
            playerspeed.innerText = this.formattedSpeed(this.speed) + 'x'; // 更新速度显示
        },
        getResolution(resolution){
            this.resolution = resolution;
            this.setResolution();
            this.setSrc();
            this.start();
        },
        setResolution(){
            if(this.resolution==null)   return;
            this.videos[this.currentVideo - 1].videosrc = this.videos[this.currentVideo - 1].videosrc.replace(/high|intermediate|mid|low/, this.resolution);
            const playerResolution = document.getElementById('player-resolution');
            playerResolution.innerText = this.formattedResolution(this.resolution); // 更新速度显示
        },
        setSrc(){
            const videosrc=this.videos[this.currentVideo-1].videosrc;
            var hls = new Hls();
            hls.loadSource(videosrc);
            const video = document.getElementById('video-player');
            hls.attachMedia(video);
        },
        soundlisten(){
            const soundButton = document.getElementById('player-sound');
            const soundInputs = document.getElementById('player-sound-inputs');
            let timeoutId; //计时
            soundButton.addEventListener('mouseover', () => {
                soundInputs.style.display = 'flex';
            });
            soundButton.addEventListener('mouseout', () => {
                timeoutId = setTimeout(() => {
                    soundInputs.style.display = 'none';
                }, 300); // 设置延迟 0.3 秒后隐藏 speedOptions
            });
            soundInputs.addEventListener('mouseover', () => {
                clearTimeout(timeoutId); // 清除之前的计时器，防止隐藏
            });
            soundInputs.addEventListener('mouseout', () => {
                timeoutId = setTimeout(() => {
                    soundInputs.style.display = 'none';
                }, 300); // 设置延迟 0.3 秒后隐藏 speedOptions
            });
        },
        resolutionlisten(){
            const resolutionButton = document.getElementById('player-resolution');
            const resolutionOptions = document.getElementById('player-resolution-options');
            let timeoutId; //计时
            resolutionButton.addEventListener('mouseover', () => {
                resolutionOptions.style.display = 'block';
            });
            resolutionButton.addEventListener('mouseout', () => {
                timeoutId = setTimeout(() => {
                    resolutionOptions.style.display = 'none';
                }, 300); // 设置延迟 0.3 秒后隐藏 resolutionOptions
            });
            resolutionOptions.addEventListener('mouseover', () => {
                clearTimeout(timeoutId); // 清除之前的计时器，防止隐藏
            });
            resolutionOptions.addEventListener('mouseout', () => {
                timeoutId = setTimeout(() => {
                    resolutionOptions.style.display = 'none';
                }, 300); // 设置延迟 0.3 秒后隐藏 resolutionOptions
            });
            const buttons = document.querySelectorAll('.player-resolution-option');
            buttons.forEach(button => {
                button.addEventListener('click', () => {
                    // 移除所有按钮的选中状态
                    buttons.forEach(btn => btn.classList.remove('selected'));

                    // 将当前点击的按钮设为选中状态
                    button.classList.add('selected');
                });
            });
        },
        speedlisten(){ 
            const speedButton = document.getElementById('player-speed');
            const speedOptions = document.getElementById('player-speed-options');
            let timeoutId; //计时
            speedButton.addEventListener('mouseover', () => {
                speedOptions.style.display = 'block';
            });
            speedButton.addEventListener('mouseout', () => {
                timeoutId = setTimeout(() => {
                    speedOptions.style.display = 'none';
                }, 300); // 设置延迟 0.3 秒后隐藏 speedOptions
            });
            speedOptions.addEventListener('mouseover', () => {
                clearTimeout(timeoutId); // 清除之前的计时器，防止隐藏
            });
            speedOptions.addEventListener('mouseout', () => {
                timeoutId = setTimeout(() => {
                    speedOptions.style.display = 'none';
                }, 300); // 设置延迟 0.3 秒后隐藏 speedOptions
            });
            const buttons = document.querySelectorAll('.player-speed-option');
            buttons.forEach(button => {
                button.addEventListener('click', () => {
                    // 移除所有按钮的选中状态
                    buttons.forEach(btn => btn.classList.remove('selected'));

                    // 将当前点击的按钮设为选中状态
                    button.classList.add('selected');
                });
            });
        },
    },

    mounted(){
        this.islogin();
        this.handleFindVideoByPage();
        this.listenTime();
        this.speedlisten();
        this.resolutionlisten();
        this.soundlisten();
        this.listenProgressbar();
        window.addEventListener('wheel', this.handleScroll);
    },
    beforeUnmount(){
        window.removeEventListener('wheel', this.handleScroll);
        this.notListenTime();
    }
}
</script>
<style>
#video-container{
    width: 100%;
    height: 100%;
    min-width: 700px;
}
#video-head{
    width: 100%;
    height: 3%;
    background-color: rgb(17,17,17);
}
#video-body{
    width: 100%;
    height: 97%;
    min-height: 595px;
    background-color: rgb(17,17,17);
}
#video-left{
    width: 8%;
    height: 98%;
    min-width: 70px;
    float: left;
    background-color: rgb(35,35,35);
    margin-right: 2%;
    border-radius: 10px;
}
#video-mid{
    width: 85%;
    height: 98%;
    min-width: 420px;
    float: left;
    background-color:rgb(35,35,35);
    border-radius: 15px;
    position: relative;
}
/* #video-mid::before{
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: url("../../images/sys/img.jpeg");
    background-size: cover;
    background-position: center;
    filter: blur(50px); 
    z-index: 100;

} */
#video-player-container{
    width: 100%;
    height: 100%;
    position: relative;
}
#video-player{
    width: 100%;
    height: 94%;
    cursor: pointer;
}
#player-status-2{
    display: none;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translateX(-50%) translateY(-50%); /* 水平竖直居中 */
    z-index: 2;
    width: 120px;
}
#video-information{
    position: absolute;
    bottom: 60px;
    left: 20px;
    color: white;
}
#video-information-head{
    overflow: hidden;
}
#video-author-name{
    font-size: 20px;
    float: left;
}
#video-time{
    font-size: 14px;
    margin-left: 5px;
    float: left;
}
#video-title{
    margin-top: 5px;
    font-size: 17px;
}
#video-player-controller{
    width: 100%;
    height: 40px;
    position: absolute;
    bottom: 10px;
}
#video-player-controller-body{
    display: flex;
}
#player-status{
    height: 30px;
    cursor: pointer;
}
#player-time{
    font-size: 17px;
    color: #E6E6E6;
    padding-top: 5px;
}
#player-continue{
    display: flex;
    cursor: pointer;
    margin-left: auto;
    margin-right: 20px;
    padding-top: 5px;
}
#player-continue-button{
    height: 20px;
    width: 35px;
    margin-right: 3px;
    padding-top: 2px;
}
#player-continue-label{
    font-size: 17px;
    color: #E6E6E6;
}
#player-clear{
    cursor: pointer;
    display: flex;
    margin-right: 20px;
    padding-top: 5px;
}
#player-clear-button{
    height: 20px;
    width: 35px;
    margin-right: 3px;
    padding-top: 2px;
}
#player-clear-label{
    font-size: 17px;
    color: #E6E6E6;
}
#player-speed-container{
    margin-right: 20px;
    position: relative;
    width: 45px;
}
#player-speed{
    font-size: 17px;
    color: #E6E6E6;
    padding-top: 5px;
    text-align: center;
    cursor: pointer;
}
#player-speed-options{
    display: none;
    position: absolute;
    bottom:calc(100% + 20px); /* 位于目标元素的正上方，加上一定的间距 */
    left: 50%; /* 水平居中 */
    transform: translateX(-50%); /* 水平居中 */
    background-color: rgb(51, 52, 63);
    text-align: center;
    width: 50px;
    border-radius: 10px;
}
#player-speed-options .player-speed-option:first-child{
    margin-top: 10px;
}
#player-speed-options .player-speed-option:last-child{
    margin-bottom: 10px;
}
.player-speed-option{
    cursor: pointer;
    color: #E6E6E6;
    font-size: 14px;
    border: none;
    padding: 0;
    outline: none;
    background-color:rgb(51, 52, 63);
    margin-top: 5px;
    margin-bottom: 5px;
}
.player-speed-option.selected{
    color: rgb(255, 44, 85);
}
.player-speed-option:hover {
    color: rgb(255, 44, 85);
}
#player-resolution-container{
    margin-right: 20px;
    position: relative;
    width: 45px;
}
#player-resolution{
    font-size: 17px;
    color: #E6E6E6;
    padding-top: 5px;
    text-align: center;
    cursor: pointer;
}
#player-resolution-options{
    display: none;
    position: absolute;
    bottom:calc(100% + 20px); /* 位于目标元素的正上方，加上一定的间距 */
    left: 50%; /* 水平居中 */
    transform: translateX(-50%); /* 水平居中 */
    background-color: rgb(51, 52, 63);
    text-align: center;
    width: 50px;
    border-radius: 10px;
}
#player-resolution-options .player-resolution-option:first-child{
    margin-top: 10px;
}
#player-resolution-options .player-resolution-option:last-child{
    margin-bottom: 10px;
}
.player-resolution-option{
    cursor: pointer;
    color: #E6E6E6;
    font-size: 14px;
    border: none;
    padding: 0;
    outline: none;
    background-color:rgb(51, 52, 63);
    margin-top: 5px;
    margin-bottom: 5px;
}
.player-resolution-option.selected{
    color: rgb(255, 44, 85);
}
.player-resolution-option:hover {
    color: rgb(255, 44, 85);
}
#player-sound-container{
    margin-right: 20px;
    position: relative;
}
#player-sound{
    height: 30px;
    cursor: pointer;
}
#player-sound-inputs {
    display: none;
    position: absolute;
    bottom: calc(100% - 10px);
    left: 50%;
    transform:rotate(270deg) translateY(-50%) ;
    transform-origin: top left;
    background-color: rgb(51, 52, 63);
    height: 30px;
    align-items: center;
    justify-content: center;
    border-radius: 5px;

}
#player-sound-input{
    margin-left: 10px;
    width: 100px;
    color: orange;
}
#player-sound-display{
    font-size: 14px;
    transform: rotate(-270deg); /* 相反地旋转回来 */
    color: #E6E6E6;
    width: 25px;
    text-align: center;
}
#player-full{
    height: 30px;
    cursor: pointer;
    margin-right: 20px;
}
#video-player-right{
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
    right: 20px;
    top: 35%;
}
#player-avatar{
    height: 40px;
    width: 40px;
    border-radius: 50%;
    cursor: pointer;
    margin-bottom: 20px;
}
#player-like{
    height: 30px;
    cursor: pointer;
}
#player-like-num{
    color: white;
    width: 100%;
    display: flex;
    justify-content: center;
    margin-bottom: 10px;
}
#player-comment{
    height: 30px;
    cursor: pointer;
}
#player-comment-num{
    color: white;
    width: 100%;
    display: flex;
    justify-content: center;
}
#video-right{
    width: 30%;
    height: 98%;
    min-width: 175;
    float: left;
    background-color:rgb(35,35,35);
    display: none;
    border-top-right-radius: 15px;
    border-bottom-right-radius: 15px;
    /* border-left: 3px solid rgb(17, 17, 17); */
}
#video-right-head{
    color: white;
    height: 5%;
    width: 100%;
    display: flex;
    justify-content: space-between;
}
#video-right-body{

    height: 95%;
    width: 100%;
}
#video-right-right{
    width: 5%;
    height: 100%;
    background-color: rgb(17,17,17);
    float: left;
    display: flex;
    justify-content: center;
    align-items: center;
}
#right-navigation{
    width: 40px;
    height: 80px;
    border-radius: 25px;
    background-color: rgb(43, 43, 55);
    position: relative;
}
#player-next{
    width: 20px;
    position: absolute;
    left: 10px;
    bottom: 10px;
    cursor: pointer;
}
#player-back{
    width: 20px;
    position: absolute;
    top: 10px;
    left: 10px;
    cursor: pointer;
}
</style>