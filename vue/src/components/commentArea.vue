<template>
    <div id="commentArea">
        <div id="commentArea-head">
            <label>全部评论{{ '('+commentNum+')' }}</label>
        </div>
        <div id="commentArea-body">
            <Comment v-for="(comment,index) in comments" :avatarsrc="comment.avatarsrc" :name="comment.name" :content="comment.content" :time="comment.time" :commentid="comment.commentid"></Comment>
        </div>
        <div id="commentArea-foot">
            <div id="commentArea-foot-container">
                <input  id="comment-input"  v-model="content" placeholder="留下你的精彩评论吧">
                <img id="comment-submit" @click="comment" src="../../images/sys/submit.png">
            </div>
            <login ref="loginRef"></login>
        </div>
    </div>
</template>
<script>
import Comment from './comment.vue';
import { commentVideo } from '../api/commentVideo';
import { islogin } from '../api/user';
import login from '../components/login.vue';
export default{
    components:{
        Comment,
        login,
    },
    name:'CommentArea',
    props:{
        comments:Array,
        commentNum:Number,
        videoId:String,

    },
    data(){
        return{
            userid:null,
            parent:'',
            content:'',
        }
    },
    mounted(){

    },
    methods:{
        comment(){
            islogin()
            .then(response=>{
                const userid = response.data.data;
                if(!userid){
                    this.$refs.loginRef.modalStatus=true;
                    return;
                }
                commentVideo({videoid:this.videoId,parent:null,content:this.content})
                .then(response=>{
                    this.$emit('comment',this.content)
                    this.content='';
                })
            }) 
        },
    }
}
</script>
<style>
#commentArea{
    width: 100%;
    height: 100%;
    position: relative;
}
#commentArea-head{
    width: 100%;
    height: 3%;
    color: white;
    font-size: 13px;
}
#commentArea-body{
    width: 100%;
    height: 97%;
    overflow: auto;
}
#commentArea-foot{
    width: 100%;
    position: absolute;
    bottom: 0px;
    height: 8%;
    display: flex;
    justify-content: center;
}
#commentArea-foot-container{
    width: 90%;
    height: 70%;
    position: relative;
}
#comment-input{
    width: 75%;
    padding-top: 0px;
    padding-bottom: 0px;
    padding-left: 5%;
    padding-right: 20%;
    border: none;
    height: 100%;
    border-radius: 15px;
    outline: none;
    font-size: 15px;
}
#comment-submit{
    height: 70%;
    border-radius: 50%;
    position: absolute;
    right: 5%;
    top: 15%;
    cursor: pointer;
}
</style>