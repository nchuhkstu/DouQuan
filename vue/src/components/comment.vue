<template>
    <div :id="'comment-'+commentid" class="comment">
        <div id="comment-left">
            <img id="comment-avatar" :src="avatarUrl">
        </div>
        <div id="comment-mid">
            <div id="comment-mid-head">
                <div id="comment-name">{{ name }}：</div>
                <div id="comment-content">{{ content }}</div>
            </div>
            <div id="comment-mdi-body">
                <div id="comment-time">{{ formttedTime(commentTime) }}</div>
            </div>
        </div>
        <div id="comment-right" v-show="display_deleteButton">
            <div id="comment-right-button" @click="handleDeleteComment"></div>
        </div>
    </div>
</template>
<script>
import { deleteComment } from '../api/article/articleComment';
import { getUserid } from '../api/user/userAccount';
export default{
    name:'Comment',
    props:{
        comment:Object,
    },
    data(){
        return{
            display_deleteButton:false,
            name:this.comment.user.name,
            userid:this.comment.user.userid,
            avatarUrl:this.comment.user.avatarUrl,
            commentTime:this.comment.commentTime,
            content:this.comment.content,
            commentid:this.comment.commentid,
        }
    },
    methods:{
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
        handleDeleteComment(){
            deleteComment(this.commentid).then(response=>{
                if(response.data.code==200){
                    this.$emit('deleteComment',this.commentid);
                }
            })
        },
        displayDeleteButton(){
            getUserid().then(response=>{
                if(this.userid==response.data.data){
                    this.display_deleteButton=true;
                }
            })
        },
    },
    mounted(){
        this.displayDeleteButton();
    }
}
</script>
<style src="../css/comment.css" scoped></style>