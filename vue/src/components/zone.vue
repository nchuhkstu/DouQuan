<template>
    <div id="zone">
        <div id="zone-head">
            <div id="zone-head-container">
                <img id="zone-avatar" :src="information.avatarUrl">
                <div id="zone-information">
                    <div class="zone-information" id="name">{{ information.name }}</div>
                    <div class="zone-information">
                        <label class="label1">关注</label><div class="zone-interaction">{{ focusNumber }}</div>
                        <label class="label1">粉丝</label><div class="zone-interaction">{{ fanNumber }}</div>
                        <label class="label1">获赞</label><div class="zone-interaction">{{ likeNumber }}</div>
                    </div>
                    <div class="zone-information">
                        <label class="label2">抖圈号：</label><div>{{ information.account }}</div>
                        <div class="label3" v-if="information.birthday">{{ calculateAge(information.birthday) }}</div>
                        <div class="label3" v-if="information.gender">{{ information.gender }}</div>
                    </div>
                    <div class="zone-information">
                        <label class="label2">注册时间：</label><div>{{ formttedTime(information.registerTime) }}</div>
                    </div>
                    <div class="zone-information">{{ information.selfIntroduction }}</div>
                </div>
                <div id="zone-set">
                    <div class="zone-set" v-if="userid==null" @click="editInformation">编辑资料</div>
                    <div class="zone-set" v-if="userid==null">隐私设置</div>
                    <div class="zone-set" v-if="userid==null">账户设置</div>
                </div>
            </div>
        </div>
        <div id="zone-foot-navigation">
            <div @click="clickFootLabel" class="foot-label selected">动态 {{ article_total_Num }}</div>
            <div @click="clickFootLabel" class="foot-label">视频</div>
            <div @click="clickFootLabel" class="foot-label">喜欢</div>
        </div>
        <informationEdit ref="informationEditRef" v-if="information.selfIntroduction!=null" :user="information"></informationEdit>
        <div id=""></div>
        <div id="container">
            <div id="article-container">
                <Article v-for="article in articles" :key="article" :article="article" :displayDelete="true" @delete="handleDelete"></Article>
            </div>
        </div>
        <div id="zone-foot">暂时没有更多了</div>
    </div>
</template>
<script>
import Article from './article.vue';
import { getDetail,getDetail2,getUserid } from '../api/user/userAccount';
import informationEdit from './informationEdit.vue';
import { getUserArticle } from '../api/user/userArticle';
export default{
    name:'zone',
    props:{
        userid:Number,
    },
    components:{
        informationEdit,
        Article,
    },
    data(){
        return{
            information:{
                avatarUrl:null,
                name:null,
                birthday:null,
                gender:null,
                account:null,
                registerTime:null,
                selfIntroduction:null,
            },

            focusNumber:0,
            fanNumber:0,
            likeNumber:0,

            test:null,
            articles:[],
            videos:null,
            pageSize:10,
            article_currentPage:1,
            article_totalPage:null,
            article_total_Num:null,
            loading:false,
        }
    },
    methods:{
        changeWorksListen(){
            const items = document.querySelectorAll('.zone-body-review-information');
            items.forEach(function(item) {
                item.addEventListener('click', function() {
                    var isActive = this.classList.contains('active');
                    items.forEach(function(item) {
                        item.classList.remove('active');
                    });
                    if (isActive) {
                        this.classList.remove('active');
                    } 
                    else {
                        this.classList.add('active');
                    }
                });
            });
        },
        async getDetail(){
            let response=null;
            if(this.userid==null){
                response = await getDetail();
                this.handleResponse(response);
            }
            else{
                response = await getDetail2(this.userid);
            }
            this.handleResponse(response);
        },
        editInformation(){
            this.$refs.informationEditRef.openModal();
        },
        handleResponse(response){
            const user = response.data.data;
            this.information.avatarUrl = user.userExtendDetail.avatarUrl;
            this.information.name = user.userExtendDetail.name;
            this.information.birthday = user.birthday;
            this.information.gender = user.gender;
            this.information.account = user.account;
            this.information.registerTime = user.registerTime;
            this.information.selfIntroduction = user.selfIntroduction;
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
        calculateAge(time){
            const birthYear = new Date(time).getFullYear();
            const currentYear = new Date().getFullYear();
            return (currentYear - birthYear) + '岁';
        },
        async handleGetUserArticle(){
            let userid=null;
            if(this.userid==null){
                const response = await getUserid();
                userid = response.data.data;
            }
            else{
                userid=this.userid;
            }
            this.loading = true;
            getUserArticle(userid,this.pageSize,this.article_currentPage).then(response=>{
                this.article_totalPage = Math.floor(response.data.data.total / this.pageSize) + 1;
                this.article_total_Num = response.data.data.total;
                for(let i=0;i<response.data.data.data.length;i++){
                    this.articles.push(response.data.data.data[i]);
                }
                this.loading = false;
            })
        },
        handleScroll() {
            const scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            const windowHeight = window.innerHeight;
            const documentHeight = Math.max(
            document.documentElement.clientHeight,
            document.documentElement.scrollHeight,
            document.body.clientHeight,
            document.body.scrollHeight);
            if (scrollTop + windowHeight >= documentHeight*0.8&&!this.loading) {
                this.loading = false;
                this.nextPage();
            }
        },
        clickFootLabel(){
            const buttons = document.querySelectorAll('.foot-label');
            buttons.forEach(button => {
                button.addEventListener('click', () => {
                    buttons.forEach(btn => btn.classList.remove('selected'));
                    button.classList.add('selected');
                });
            });   
        },
        nextPage(){
            if(this.article_currentPage<this.article_totalPage){
                this.article_currentPage++;
                this.handleGetUserArticle();
            }
        },
        handleDelete(articleid){
            for (let i = 0; i < this.articles.length; i++) {
                if (this.articles[i].article.articleid == articleid) {
                    this.articles.splice(i, 1);
                    this.article_total_Num--;
                    break;
                }
            }
        }
    },
    mounted(){
        this.getDetail();
        this.changeWorksListen();
        this.handleGetUserArticle();
        window.addEventListener('scroll', this.handleScroll);
    }
}
</script>
<style>
#zone{
    width: 100%;
    min-width: 1050px;
    min-height: 100%;
    background-color: rgb(22,25,35);
}
#zone-head{
    width: 100%;
    height: 35vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background-image: url('/images/sys/Zone_background.jpg');
    background-repeat: no-repeat;
    background-position: center;
    background-size: 100% auto;
}
#zone-head-container{
    height: 60%;
    width: 80%;
    display: flex;
}
#zone-avatar{
    height: 100%;
    border-radius: 50%;
    margin-right: 3vw;
}
#zone-information{
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
}
.zone-information{
    display: flex;
}
#name{
    font-size: 3.5vh;
}
.label1{
    font-size: 2.5vh;
    line-height: 2.5vh;
    margin-right: 1vw;
}
.label2{
    font-size: 2vh;
}
.label3{
    min-width: 2.5vw;
    text-align: center;
    border-radius: 0.5vh;
    background-color: rgb(80,80,80);
    font-size: 1vh;
    height: 2.5vh;
    line-height: 2.5vh;
    margin: auto 0;
    margin-left: 2vw;
    color: white;
}
.zone-interaction{
    font-size: 3vh;
    line-height: 2.5vh;
    margin-right: 40px;
}
#zone-set{
    height: 60%;
    margin-left: auto;
}
.zone-set{
    border-radius: 1vw;
    padding: 0.5vh 1vw;
    border: 1px solid black;
    margin-bottom: 1vh;
    font-size: 2.5vh;
    cursor: pointer;
}
#zone-foot-navigation{
    width: 80%;
    padding: 0 10%;
    color: black;
    display: flex;
    margin-top: 2vh;
    margin-bottom: 4vh;
}
.foot-label{
    font-size: 3vh;
    margin-right: 6vw;
    cursor: pointer;
    color:rgb(101,103,110)
}
.foot-label.selected{
    color: white;
}
#container{
    width: 100%;
    margin: 0 auto;
}
#article-container{
    width: 50%;
    margin: 0 auto;
}
#zone-foot{
    text-align: center;
    padding-top: 2vh;
    padding-bottom: 5vh;
    color:rgb(101,103,110)
}
</style>