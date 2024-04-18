<template>
    <div id="videoPublishContainer">
      <input id="video-publish-title" v-model="videoTitle">
      <input id="video-publish-upload-input" type="file" style="display: none;" @change="handleFileUpload($event);$event.target.value = ''">
      <button id="video-publish-button" @click="handPublish">发布</button>
      <div id="video-publish-player-container">
        <label for="video-publish-upload-input">选择视频</label>
        <video id="video-publish-player" :src="videoUrl" controls></video>
      </div>
    </div>
</template>
  
<script>
import { publishVideo, uploadVideo } from "../api/video";
import { islogin,istest } from "../api/user";
import router from "../modules/router";

export default {
data() {
    return {
        videoTitle: "",
        videoFile: null,
        videoUrl:null,
        userid:'',
        videoid:'',
        totaltime:'',
    };
},
mounted(){
    this.islogin();
},
methods: {
    islogin(){
        islogin().then(response=>{
            if(response.data.data!=null){
                this.userid = response.data.data;
            }
        })
    },
    handleFileUpload(event) {
        this.videoFile = event.target.files[0];
        // 创建一个 FileReader 对象
        const reader = new FileReader();
        reader.onload = (e) => {
            // 将读取到的视频数据赋值给 videoUrl，用于在页面上显示视频
            this.videoUrl = e.target.result;
            
            // 创建一个 video 元素
            const videoElement = document.createElement('video');
            videoElement.src = this.videoUrl;
            
            // 当视频元数据加载完成后
            videoElement.onloadedmetadata =() =>{
                // 获取视频的时长（以秒为单位）
                this.totaltime = videoElement.duration;
            };
        };
        // 读取视频文件
        reader.readAsDataURL(this.videoFile);
    },
    handPublish() {
        if (this.videoTitle === "") {
            alert("标题不能为空");
            return;
        }
        if (!this.videoFile) {
            alert("请选择视频文件");
            return;
        }
        istest().then(response=>{
            if(response.data.code==200){
                publishVideo({ title: this.videoTitle,totaltime:this.totaltime })
                .then(response =>{
                    this.videoid = response.data.data.videoid;
                    const formData = new FormData();
                    formData.append("video", this.videoFile);
                    uploadVideo(formData, {userid:this.userid,videoid:this.videoid})
                    .then((response) => {
                        console.log("视频上传成功");
                        // 在这里可以处理上传成功后的逻辑
                    })
                    .catch((error) => {
                        console.error("视频上传失败", error);
                    });
                })
            }
            else{
                alert("还未拥有发布权限，若要继续发布，请参加测试");
                router.push('./test');
            }
        })

    },
},
};
</script>
  
<style scoped>
#videoPublishContainer{
    width: 100vw;
}
#video-publish-player-container{
    width: 100%;
}
#video-publish-player{
    width: 100%;
    height: 500px;
    background-color: black;
}
</style>