<template>
    <div :id="'article-'+articleId" class="article">
        <div id="article-head">
            <img id="article-avatar" :src="userAvatarURL" @click="goZone(userId)">
            <div id="article-headright">
                <div id="article-name" @click="goZone(userId)">{{ userName }}</div>
                <div id="article-publishTime">{{ formttedTime(publishTime) }}</div>
            </div>
            <div id="article-setting" @click="handleDelete" v-if="userId==current_userid&&displayDelete==true"></div>
        </div>
        <div id="article-body">
            <div id="article-title">{{ title }}</div>
            <div id="article-content">{{ content }}</div>
            <div id="artclie-photo-container">
                <img v-for="(src,index) in images" class="article-photo" :key='src' :src='src' :class="{active: src === activePhoto}" @click="getBigPhoto(src,index)">
            </div>
        </div>
        <div id="article-foot">
            <div id="article-foot-click">
                <div @click="like()">
                    <img id="article-foot-heart" src="../../images/sys/love.png" >
                    <label class="article-foot-label">{{ likeNumber }}</label>
                </div>
                <div @click="openCommonArea">
                    <img id="article-foot-comment" src="../../images/sys/chat.png" >
                    <label class="article-foot-label">{{ commentNumber }}</label>
                </div>
                <div>
                    <img id="article-foot-share" src="../../images/sys/share.png" @click="l">
                    <label class="article-foot-label">{{ relayNumber }}</label>
                </div>
                
            </div>
            <div id="article-foot-commentArea">
                <div id="article-foot-input-container">
                    <textarea id="article-foot-input" v-model="commentcontent" type="text" placeholder="发表你的评论"></textarea>
                    <button id="article-foot-input-button" @click="handleComment">发布</button>
                </div>
                <div id="article-commentArea">
                    <Comment v-for="comment in comments" :key="comment" :comment="comment" @deleteComment="deleteComment"></Comment>
                </div>
            </div>
        </div>
        <login ref="loginRef"></login>
    </div>
</template>
<script>
import { like,disLike,isLike } from '../api/article/articleLike';
import { comment, getComment } from '../api/article/articleComment';
import { getCommentById } from '../api/comment/comment';
import { deleteUserArticle } from '../api/user/userArticle';
import Comment from './comment.vue'
import login from '../components/login.vue';
import { getUserid } from '../api/user/userAccount';
export default {
    name:'Article',
    props: {
        article:Object,
        displayDelete:Boolean,
    },
    components:{
        Comment,
        login
    },
    data(){
        return{
            activePhoto:null,               //大图
            activeIndex:null,               //目前的大图下标
            likestatu:false,                    //评论的点赞状态
            commentcontent:null,            //评论输入框的内容
            comments:[],                    //存储该文章的评论
            commentAreaStatu:0,             //评论区默认隐藏

            pageSize:10,
            currentPage:1,
            toltalPage:null,        //总页数

            viewNumber:this.article.viewNumber,
            likeNumber:this.article.likeNumber,
            commentNumber:this.article.commentNumber,
            relayNumber:this.article.relayNumber,

            userAvatarURL:this.article.user.avatarUrl,
            userName:this.article.user.name,
            userId:this.article.user.userid,

            articleId:this.article.article.articleid,
            title:this.article.article.title,
            content:this.article.article.content,
            publishTime:this.article.article.publishtime,

            images:this.article.images,

            current_userid:null,
        }
    },
    methods:{
        //打开评论区
        openCommonArea(){
            const targetArticle = document.getElementById('article-'+this.articleId);
            const targetChildElement = targetArticle.querySelector('#article-foot-commentArea');
            const targetChildElement2 = targetArticle.querySelector('#article-commentArea');
            this.comments = [];
            if(this.commentAreaStatu==0){
                targetChildElement.style.display = 'block';
                targetChildElement2.style.display = 'block';
                this.commentAreaStatu = 1;
                return new Promise((resolve)=>{
                    getComment(this.articleId,this.pageSize,this.currentPage).then(response=>{
                        this.totalPage = Math.floor(response.data.data.total / this.pageSize) + 1;
                        for (let i = 0; i < response.data.data.total - this.pageSize * (this.currentPage - 1) && i < this.pageSize; i++){
                            this.comments.push(response.data.data.data[i]);
                        }
                        resolve();
                    })
                })
            }
            else{
                targetChildElement.style.display = 'none';
                targetChildElement2.style.display = 'none';
                this.commentAreaStatu = 0;
            }
            
        },
        //下一页
        nextPage(){
            if(this.currentPage<this.totalPage){
                this.currentPage++;
                this.handleGetHot().then(()=>{
                    this.scrollEnabled = true;
                });
            }
        },
        //评论
        handleComment(){
            comment(this.articleId,{content:this.commentcontent}).then(response=>{
                if(response.data.code==504){
                    this.$refs.loginRef.openModal();
                }
                else{
                    getCommentById(response.data.data).then(response=>{
                        this.comments.push(response.data.data);
                        this.commentNumber++;
                    })
                }
            })
        },
        //查看大图
        getBigPhoto(src,index){
            if(this.activeIndex == index){
                this.activePhoto = null;
                this.activeIndex = null;
                return;
            }
            this.activeIndex = index;
            this.activePhoto = src;
        },
        //点赞
        like(){
            if(this.likestatu == false){
                like(this.articleId)
                .then(response=>{
                    if(response.data.code==504){
                        this.$refs.loginRef.openModal();
                    }
                    else{
                        this.likeNumber++;
                        const targetArticle = document.getElementById('article-'+this.articleId);
                        const targetChildElement = targetArticle.querySelector('#article-foot-heart');
                        targetChildElement.src = "../../images/sys/heart.png";
                        this.likestatu = true;
                    }
                })
            }
            else{
                disLike(this.articleId).then(response=>{
                    if(response.data.code==504){
                        this.$refs.loginRef.openModal();
                    }
                    else{
                        this.likeNumber--;
                        const targetArticle = document.getElementById('article-'+this.articleId);
                        const targetChildElement = targetArticle.querySelector('#article-foot-heart');
                        targetChildElement.src = "../../images/sys/love.png";
                        this.likestatu = false;
                    }
                })
            }
        },
        //是否点赞
        islike(){
            isLike(this.articleId)
            .then(response=>{
                if(response.data.data==true){
                    const targetArticle = document.getElementById('article-'+this.articleId);
                    const targetChildElement = targetArticle.querySelector('#article-foot-heart');
                    targetChildElement.src = "../../images/sys/heart.png";
                    this.likestatu = true;
                }
            })
        },
        formttedTime(time){
            const date = new Date(time);
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, "0");
            const day = date.getDate().toString().padStart(2, "0");
            const hours = date.getHours().toString().padStart(2, "0");
            const minutes = date.getMinutes().toString().padStart(2, "0");
            const seconds = date.getSeconds().toString().padStart(2, "0");
            return`${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
        },
        deleteComment(commentid) {
            for (let i = 0; i < this.comments.length; i++) {
                if (this.comments[i].commentid === commentid) {
                    this.comments.splice(i, 1);
                    this.commentNumber--;
                    break;
                }
            }
        },
        goZone(userid){
            this.$emit('goZone',userid);
        },
        getCurrentUserId(){
            getUserid().then(response=>{
                this.current_userid = response.data.data;
            })
        },
        handleDelete(){
            console.log(this.articleId)
            deleteUserArticle({articleid:this.articleId}).then(response=>{
                if(response.data.code==200)
                    this.$emit('delete',this.articleId);
            })
        }
    },
    mounted(){
        this.islike();
        this.getCurrentUserId();
    },
}
</script>
<style src="../css/article.css" scoped></style>