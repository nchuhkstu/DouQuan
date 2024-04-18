<template>
    <div id="articlePopup">
    <div :id="'articleCard-'+pageid" class="articleCard">
        <div id="articleCard-head">
            <img id="articleCard-avatar" :src="avatarsrc">
            <div id="articleCard-headright">
                <div id="articleCard-name">{{ name }}</div>
                <div id="articleCard-publishTime">{{ publishTime }}</div>
            </div>
        </div>
        <div id="articleCard-body">
            <div id="articleCard-title">{{ title }}</div>
            <div id="articleCard-content">{{ content }}</div>
            <div id="artclie-photo-container">
                <img v-for="(src,index) in photosrc" class="articleCard-photo" :key='src' :src='src' :class="{active: src === activePhoto}" @click="getBigPhoto(src,index)">
            </div>
        </div>
        <div id="articleCard-foot">
            <div id="articleCard-foot-click">
                <div @click="like(pageid)">
                    <img id="articleCard-foot-heart" src="../../images/sys/love.png" >
                    <label class="articleCard-foot-label">{{ likenum }}</label>
                </div>
                <div @click="openCommonArea">
                    <img id="articleCard-foot-comment" src="../../images/sys/chat.png" >
                    <label class="articleCard-foot-label">{{ commentnum }}</label>
                </div>
                <div>
                    <img id="articleCard-foot-share" src="../../images/sys/share.png" @click="l">
                    <label class="articleCard-foot-label"></label>
                </div>
                
            </div>
            <div id="articleCard-foot-commentArea">
                <div id="articleCard-foot-input-container">
                    <textarea id="articleCard-foot-input" v-model="commentcontent" type="text" placeholder="发表你的评论"></textarea>
                    <button id="articleCard-foot-input-button" @click="comment">发布</button>
                </div>
                <div id="articleCard-commentArea">
                    <Comment v-for="(comment,index) in comments" :avatarsrc="comment.avatarsrc" :name="comment.name" :content="comment.content" :time="comment.time" :commentid="comment.commentid"></Comment>
                </div>
            </div>
        </div>
    </div>
    </div>
</template>
<script>
import { like,dislike,islike} from '../api/page';
import { comment,getcomment } from '../api/comment';
import { getAvatar } from '../api/image';
import { getDetailByUserid,islogin} from '../api/user';
import Comment from './comment.vue'
export default {
    name:'articleCard',
    props: {
        avatarsrc:String,
        name:String,
        publishTime:String,
        title:String,
        content: String,
        photosrc:Array,
        userid:String,
        pageid:String,
        likeNum:String,
        commentNum:Number,
        commentid:String
    },
    components:{
        Comment
    },
    data(){
        return{
            activePhoto:null,           //大图
            activeIndex:null,           //目前的大图下标
            likenum:this.likeNum,       //点赞数量
            commentnum:this.commentNum, //评论数量
            likestatu:0,                //评论的点赞状态
            commentcontent:null,        //评论输入框的内容
            comments:[],                //存储该文章的评论
            commentAreaStatu:0,         //评论区默认隐藏
        }
    },
    mounted(){
        if(this.userid!=null){
            this.islike(this.userid.toString(),this.pageid.toString());
        }
        this.openCommonArea();
        //this.markComment();
    },
    methods:{
        markComment(){
            console.log(this.commentid);
            document.getElementById('markedComment').style.backgroundColor="rgb(237, 232, 175)";
        },
        //打开评论区
        openCommonArea(){
            const targetArticle = document.getElementById('articleCard-'+this.pageid);
            const targetChildElement = targetArticle.querySelector('#articleCard-foot-commentArea');
            const targetChildElement2 = targetArticle.querySelector('#articleCard-commentArea');
            this.comments = [];
            if(this.commentAreaStatu==0){
                targetChildElement.style.display = 'block';
                targetChildElement2.style.display = 'block';
                this.commentAreaStatu = 1;
                getcomment(this.pageid)
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
                        const year = (publishTime.getFullYear() === new Date().getFullYear()) ? "" : publishTime.getFullYear() + "/";
                        const month = (publishTime.getMonth() + 1).toString().padStart(2, "0");
                        const day = publishTime.getDate().toString().padStart(2, "0");
                        const hour = publishTime.getHours().toString().padStart(2, "0");
                        const minute = publishTime.getMinutes().toString().padStart(2, "0");
                        const time2 = `${year}${month}/${day} ${hour}:${minute}`;
                        const commentid = response.data.data[i].commentid.toString();
                        this.comments.push({avatarsrc:avatarsrc,name:name,content:content,time:time2,commentid:commentid});
                        
                    }
                })
                .then(() => {
                    // 在所有Comment组件加载完成后执行markComment函数
                    this.$nextTick(() => {
                        const marked=document.getElementById('comment-'+this.commentid);
                        if (marked && marked.closest('#articleCard-commentArea')) {
                            marked.setAttribute('id', 'markedComment')
                        }
                        this.markComment();
                    });
                });
            }
            else{
                targetChildElement.style.display = 'none';
                targetChildElement2.style.display = 'none';
                this.commentAreaStatu = 0;
            }
            
        },
        //评论
        comment(){
            islogin().then(response=>{
                const userid = response.data.data;
                if(!userid){
                    this.$refs.loginRef.modalStatus=true;
                    return;
                }
                comment({pageid:this.pageid,content:this.commentcontent})
                .then(async response=>{
                    this.commentnum++;
                    const avatarsrc = await this.getAvatar(userid.toString());
                    let name = null;
                    await getDetailByUserid({userid:userid.toString()}).then(response=>{
                        name = response.data.data.user.name;
                    })
                    const content = this.commentcontent;
                    const publishTime = new Date();
                    const year = (publishTime.getFullYear() === new Date().getFullYear()) ? "" : publishTime.getFullYear() + "/";
                    const month = (publishTime.getMonth() + 1).toString().padStart(2, "0");
                    const day = publishTime.getDate().toString().padStart(2, "0");
                    const hour = publishTime.getHours().toString().padStart(2, "0");
                    const minute = publishTime.getMinutes().toString().padStart(2, "0");
                    const time2 = `${year}${month}/${day} ${hour}:${minute}`;
                    const commentid = response.data.data.commentid.toString();
                    document.getElementById('article-foot-input').textContent='';
                    this.commentcontent='';
                    this.comments.push({avatarsrc:avatarsrc,name:name,content:content,time:time2,commentid:commentid});
                })
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
        like(pageid){
            islogin().then(response=>{
                const userid = response.data.data;
                if(!userid){
                    this.$refs.loginRef.modalStatus=true;
                    return;
                }
                if(this.likestatu ==0){
                    like({pageid:pageid})
                    .then(response=>{
                        if(response.data.code!=509){
                            this.likenum++;
                            const targetArticle = document.getElementById('article-'+pageid);
                            const targetChildElement = targetArticle.querySelector('#article-foot-heart');
                            targetChildElement.src = "../../images/sys/heart.png";
                            this.likestatu = 1;
                        }
                    })
                }
                else{
                    dislike({pageid:pageid})
                    .then(response=>{
                        this.likenum--;
                        const targetArticle = document.getElementById('article-'+pageid);
                        const targetChildElement = targetArticle.querySelector('#article-foot-heart');
                        targetChildElement.src = "../../images/sys/love.png";
                        this.likestatu = 0;
                    })
                } 
            })
        },
        //是否点赞
        islike(){
            islike({pageid:this.pageid})
            .then(response=>{
                if(response.data.code==509){
                    const targetArticle = document.getElementById('article-'+this.pageid);
                    const targetChildElement = targetArticle.querySelector('#article-foot-heart');
                    targetChildElement.src = "../../images/sys/heart.png";
                    this.likestatu = 1;
                }
            })
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
    },
}
</script>
<style src="../css/articleCard.css" scoped></style>