<template>
<div id="commentCard">
    <div :id="'commentCard-'+commentid" class="comment">
        <div id="comment-left">
            <img id="comment-avatar" :src="avatarsrc">
        </div>
        <div id="comment-right">
            <div id="comment-right-head">
                <div id="comment-name">{{ name }}：</div>
                <div id="comment-content">{{ content }}</div>
            </div>
            <div id="comment-right-body">
                <div id="comment-time">{{ time }}</div>
            </div>
        </div>
    </div>
    <div id="comment-from" @click="showArticle">
        回复来自于主题：{{ articleTitle }}
    </div>
</div>
<div id="popBorder" v-if="showPopup">
    <div id="popArea">
        <component id="articlePop" :is="popupComponent" :avatarsrc="articles[0].avatarsrc" :name="articles[0].name" :publishTime="articles[0].publishTime" :title="articles[0].title" :content="articles[0].content" :photosrc="articles[0].photosrc" :userid="articles[0].userid" :pageid="articles[0].pageid" :likeNum="articles[0].likeNum" :commentNum="articles[0].commentNum" :commentid="commentid" @closePopup="closePopup" />
        <button id="closePop" @click="closePopup">
        <svg width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
            <path d="M4.4 4.55l.07-.08a.75.75 0 01.98-.07l.08.07L12 10.94l6.47-6.47a.75.75 0 111.06 1.06L13.06 12l6.47 6.47c.27.27.3.68.07.98l-.07.08a.75.75 0 01-.98.07l-.08-.07L12 13.06l-6.47 6.47a.75.75 0 01-1.06-1.06L10.94 12 4.47 5.53a.75.75 0 01-.07-.98l.07-.08-.07.08z"></path>
        </svg>
        </button>
    </div>
</div>
</template>
<script>
import { getPage } from '../api/search';
import { getDetailByUserid } from '../api/user';
import { getAvatar,getPhoto } from '../api/image';
import { getcommentNum } from "../api/comment";
import JSZip from 'jszip';
import articleCard from './articleCard.vue';

export default{
    name:'commentCard',
    props:{
        avatarsrc:String,
        name:String,
        content:String,
        commentid:String,
        time:String,
        pageid:String
    },
    components:{
        articleCard,
    },
    data(){
        return{
            article:[],
            articles:[],
            articleTitle:'',         //标题
            popupComponent: '',
            showPopup:false,
        }
    },
    mounted(){
        this.handleComment();
    },
    methods:{
        async handleComment() {
            if (this.pageid > 0) {
                try {
                    const response = await getPage(this.pageid);
                    this.article= response.data.data;
                    this.articleTitle=this.article.title;
                } catch (error) {
                console.error(error);
                }
            } else {
                this.articleTitle = "出错啦"; // 清空用户搜索结果数组
            }
        },
        //获取头像
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
        //获取文章的图片
        getPhoto(data) {
            return new Promise((resolve, reject) => {
                getPhoto(data)
                .then(response => {
                    if (response.data.byteLength == 0) {
                    resolve([]); // 返回空的src数组
                    } else {
                    // 使用JSZip解压缩ZIP文件
                    return JSZip.loadAsync(response.data);
                    }
                })
                .then(zip => {
                    if (!zip) {
                    resolve([]); // 返回空的src数组
                    } else {
                    // 存储图片数据的数组
                    const imagePromises = [];
                    
                    // 遍历ZIP文件中的文件
                    zip.forEach((relativePath, file) => {
                        const allowedExtensions = ['.jpg', '.png', '.webp','jpeg','heif'];
                        if (allowedExtensions.some(ext => file.name.toLowerCase().endsWith(ext))) {                            // 读取图片文件数据
                            const imagePromise = file.async('base64').then(data => {
                                return {
                                name: file.name,
                                data: `data:${file.type};base64,${data}`
                                };
                            });
                            imagePromises.push(imagePromise);
                        }
                    });
                    // 等待所有图片数据读取完成
                    Promise.all(imagePromises)
                        .then(images => {
                        // 返回所有图片的src数组
                        const srcArray = images.map(image => image.data);
                        resolve(srcArray);
                        })
                        .catch(error => {
                        reject(error);
                        });
                    }
                })
                .catch(error => {
                    reject(error);
                });
            });
        },
        async showArticle() {
        // 弹出窗口并展示 article 中的信息
            const response1 = await getDetailByUserid({ userid: this.article.userid.toString() });
            const src = await this.getAvatar(this.article.userid.toString());
            const photosrc = await this.getPhoto({userid: this.article.userid.toString(), pageid: this.article.pageid.toString()});
            const publishTime = new Date(this.article.publishtime);
            const year = (publishTime.getUTCFullYear() === new Date().getUTCFullYear()) ? "" : publishTime.getUTCFullYear() + "/";
            const month = (publishTime.getUTCMonth() + 1).toString().padStart(2, "0");
            const day = publishTime.getUTCDate().toString().padStart(2, "0");
            const hour = publishTime.getUTCHours().toString().padStart(2, "0");
            const minute = publishTime.getUTCMinutes().toString().padStart(2, "0");
            const time = `${year}${month}/${day} ${hour}:${minute}`;
            const s = this.article.likelist;
            const a = s.split(",");
            let likeNum = a.length;
            let commentNum = 0;
            const commentResponse = await getcommentNum(this.article.pageid);
            commentNum = commentResponse.data.data;
            if (s.length == 0) {
                likeNum = likeNum - 1;
            }
            this.articles.push({ 
                avatarsrc: src,
                name: response1.data.data.user.name, 
                publishTime: time, 
                title: this.article.title, 
                content: this.article.content,
                photosrc:photosrc,
                pageid:this.article.pageid.toString(),
                likeNum:likeNum.toString(),
                commentNum:commentNum
            });
            this.popupComponent=articleCard;
            this.showPopup = true;
        },
        closePopup() {
            // 关闭弹窗
            this.showPopup = false;
        },
    },
}
</script>
<style src="../css/commentCard.css" scoped></style>