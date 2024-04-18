<template>
<div id="ladder">
    <div id="ladder-box">
        <button id="hot-button" class="changeButton" @click="hotClicked">
            热榜
        </button>
        <button id="like-button" class="changeButton" @click="likeClicked">
            点赞
        </button>
        <button id="view-button" class="changeButton" @click="viewClicked">
            浏览
        </button>
        <button id="comment-button" class="changeButton" @click="commentClicked">
            评论
        </button>
    </div>
    <div id="ladder-items">
        <div id="hot-ladder" class="ladderArea" v-show="isHot" v-if="hotResults.length>0">
            <div v-for="(result, index) in hotResults" :key="index" class="ladder-column" @click="handleColumnClick(result)">
                <div class="column">
                    <div class="column-title">
                        <img v-if="index==0" class="orderImg" src="../../images/sys/first.png">
                        <img v-else-if="index==1" class="orderImg" src="../../images/sys/second.png">
                        <img v-else-if="index==2" class="orderImg" src="../../images/sys/third.png">
                        <div v-else class="order">
                            {{ index+1 }}
                        </div>
                        {{ changeTooLongString(result.title) }}
                    </div>
                    <div class="column-count">
                        {{ result.count }} 热度
                    </div>
                </div>
            </div>
        </div>
        <div v-else class="notFind" v-show="isHot">
            <img class="sorryImg" src="../../images/sys/sorry.png">
        </div>
        <div id="like-ladder" class="ladderArea" v-show="isLike" v-if="likeResults.length>0">
            <div v-for="(result, index) in likeResults" :key="index" class="ladder-column" @click="handleColumnClick(result)">
                <div class="column">
                    <div class="column-title">
                        <img v-if="index==0" class="orderImg" src="../../images/sys/first.png">
                        <img v-else-if="index==1" class="orderImg" src="../../images/sys/second.png">
                        <img v-else-if="index==2" class="orderImg" src="../../images/sys/third.png">
                        <div v-else class="order">
                            {{ index+1 }}
                        </div>
                        {{ changeTooLongString(result.title) }}
                    </div>
                    <div class="column-count">
                        {{ result.count }} 喜欢
                    </div>
                </div>
            </div>
        </div>
        <div v-else class="notFind" v-show="isLike">
            <img class="sorryImg" src="../../images/sys/sorry.png">
        </div>
        <div id="comment-ladder" class="ladderArea" v-show="isComment" v-if="commentResults.length>0">
            <div v-for="(result, index) in commentResults" :key="index" class="ladder-column" @click="handleColumnClick(result)">
                <div class="column">
                    <div class="column-title">
                        <img v-if="index==0" class="orderImg" src="../../images/sys/first.png">
                        <img v-else-if="index==1" class="orderImg" src="../../images/sys/second.png">
                        <img v-else-if="index==2" class="orderImg" src="../../images/sys/third.png">
                        <div v-else class="order">
                            {{ index+1 }}
                        </div>
                        {{ changeTooLongString(result.title) }}
                    </div>
                    <div class="column-count">
                        {{ result.count }} 评论
                    </div>
                </div>
            </div>
        </div>
        <div v-else class="notFind" v-show="isComment">
            <img class="sorryImg" src="../../images/sys/sorry.png">
        </div>
        <div id="view-ladder" class="ladderArea" v-show="isView" v-if="viewResults.length>0">
            <div v-for="(result, index) in viewResults" :key="index" class="ladder-column" @click="handleColumnClick(result)">
                <div class="column">
                    <div class="column-title">
                        <img v-if="index==0" class="orderImg" src="../../images/sys/first.png">
                        <img v-else-if="index==1" class="orderImg" src="../../images/sys/second.png">
                        <img v-else-if="index==2" class="orderImg" src="../../images/sys/third.png">
                        <div v-else class="order">
                            {{ index+1 }}
                        </div>
                        {{ changeTooLongString(result.title) }}
                    </div>
                    <div class="column-count">
                        {{ result.count }} 浏览
                    </div>
                </div>
            </div>
        </div>
        <div v-else class="notFind" v-show="isView">
            <img class="sorryImg" src="../../images/sys/sorry.png">
        </div>
    </div>
</div>
</template>
<script>
import { getHotLadder,getLikeLadder,getViewLadder,getCommentLadder} from "../api/ladder";
export default {
    name:'ladder',
    components:{
    },
    data() {
        return {
            isFetchingData:false,   //异步函数信号标志
            hotResults:[],
            likeResults:[],
            commentResults:[],
            viewResults:[],
            isHot:true,
            isComment:false,
            isLike:false,
            isView:false,
        };
    },
    mounted(){
        this.handleHotLadder();
    },
    methods: {
        async handleHotLadder() {
            if (this.isFetchingData) {
                await new Promise(resolve => setTimeout(resolve, 5000));
            }
            this.isFetchingData = true;
            try {
                if(this.hotResults){
                    this.hotResults=[];
                }
                const response = await getHotLadder();
                console.log(response.data.data);
                for(let i=0;i<response.data.data.length;i++){
                    this.hotResults.push({
                    title:response.data.data[i].title,
                    count:response.data.data[i].hot,
                });
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.isFetchingData = false;
            }
        },
        async handleLikeLadder() {
            if (this.isFetchingData) {
                await new Promise(resolve => setTimeout(resolve, 5000));
            }
            this.isFetchingData = true;
            try {
                if(this.likeResults){
                    this.likeResults=[];
                }
                const response = await getLikeLadder();
                console.log(response.data.data);
                for(let i=0;i<response.data.data.length;i++){
                        this.likeResults.push({
                        title:response.data.data[i].title,
                        count:response.data.data[i].like,
                    });
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.isFetchingData = false;
            }
        },
        async handleViewLadder() {
            if (this.isFetchingData) {
                await new Promise(resolve => setTimeout(resolve, 5000));
            }
            this.isFetchingData = true;
            try {
                if(this.viewResults){
                    this.viewResults=[];
                }
                const response = await getViewLadder();
                for(let i=0;i<response.data.data.length;i++){
                        this.viewResults.push({
                        title:response.data.data[i].title,
                        count:response.data.data[i].view,
                    });
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.isFetchingData = false;
            }
        },
        async handleCommentLadder() {
            if (this.isFetchingData) {
                await new Promise(resolve => setTimeout(resolve, 5000));
            }
            this.isFetchingData = true;
            try {
                if(this.commentResults){
                    this.commentResults=[];
                }
                const response = await getCommentLadder();
                for(let i=0;i<response.data.data.length;i++){
                        this.commentResults.push({
                        title:response.data.data[i].title,
                        count:response.data.data[i].count,
                    });
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.isFetchingData = false;
            }
        },
        changeTooLongString(data) {
            if (data.length > 12) {
                data = data.slice(0, 12) + '...';
            }
            return data;
        },
        async handleColumnClick(result){
            // 设置新的 searching 值
            //this.$emit('update-searching', result);
            console.log(result);
        },
        setButtonColor(){
            if(!this.isHot){
                document.getElementById("hot-button").style.fontWeight="normal";
                document.getElementById("hot-button").style.border="0";
                document.getElementById("hot-button").style.color="rgba(223, 226, 228, 0.717)";
                
            }
            if(!this.isLike){
                document.getElementById("like-button").style.fontWeight="normal";
                document.getElementById("like-button").style.border="0";
                document.getElementById("like-button").style.color="rgba(223, 226, 228, 0.717)";
            }
            if(!this.isView){
                document.getElementById("view-button").style.fontWeight="normal";
                document.getElementById("view-button").style.border="0";
                document.getElementById("view-button").style.color="rgba(223, 226, 228, 0.717)";
            }
            if(!this.isComment){
                document.getElementById("comment-button").style.fontWeight="normal";
                document.getElementById("comment-button").style.border="0";
                document.getElementById("comment-button").style.color="rgba(223, 226, 228, 0.717)";
            }
        },
        hotClicked(){
            document.getElementById("hot-button").style.borderBottom="2px solid rgb(254, 20, 20)";
            document.getElementById("hot-button").style.fontWeight="bold";
            document.getElementById("hot-button").style.color="rgb(254, 235, 235)";
            this.isHot=true;
            this.isLike=false;
            this.isView=false;
            this.isComment=false;
            this.setButtonColor();
            this.handleHotLadder();
        },
        likeClicked(){
            document.getElementById("like-button").style.borderBottom="2px solid rgb(254, 20, 20)";
            document.getElementById("like-button").style.fontWeight="bold";
            document.getElementById("like-button").style.color="rgb(254, 235, 235)";
            this.isHot=false;
            this.isLike=true;
            this.isView=false;
            this.isComment=false;
            this.setButtonColor();
            this.handleLikeLadder();
        },
        viewClicked(){
            document.getElementById("view-button").style.borderBottom="2px solid rgb(254, 20, 20)";
            document.getElementById("view-button").style.fontWeight="bold";
            document.getElementById("view-button").style.color="rgb(254, 235, 235)";
            this.isHot=false;
            this.isLike=false;
            this.isView=true;
            this.isComment=false;
            this.setButtonColor();
            this.handleViewLadder();
        },
        commentClicked(){
            document.getElementById("comment-button").style.borderBottom="2px solid rgb(254, 20, 20)";
            document.getElementById("comment-button").style.fontWeight="bold";
            document.getElementById("comment-button").style.color="rgb(254, 235, 235)";
            this.isHot=false;
            this.isLike=false;
            this.isView=false;
            this.isComment=true;
            this.setButtonColor();
            this.handleCommentLadder();
        },
    },
};
</script>
<style src="../css/ladder.css" scoped></style>
    
    