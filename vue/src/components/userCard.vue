<template>
    <div :id="'userCard-'+userid" class="user-search-result-item" @click="handleUserSearchResultClick(userCard)" >
        <div id="userTitle">
            <div id="portrait">
                <img :src="portraitsrc">
             </div>
             <div id="name-value">{{ name }}</div>
             <button id="follow-button">关注</button>
        </div>
        <div id="user-info">
            <div id="info-label">抖圈号:</div>
            <div id="info-value">{{ username }}</div>
            <div id="apart">
                |
            </div>
            <div id="city-value" v-if="logincity">
                {{ logincity }}
            </div>
            <div  id="city-value" v-else>
                未知
            </div>
        </div>
        <div id="self-info">
            <div v-if="selfintroduction" id="self-value">{{ selfintroduction }}</div>
            <div v-else  id="self-value">该用户暂未填写个人简介</div>
        </div>
        <div id="sign-time">
            <div id="time-label">注册时间:</div>
            <div id="time-value">{{ registrationtime }}</div>
        </div>
    </div>
</template>
<script>
import router from '../modules/router';
import { getAvatar } from '../api/image';
export default {
    name:'userCard',
    props: {
        portraitsrc:String,
        userid:String,
        username:String,
        name:String,
        logincity:String,
        selfintroduction: String,
        registrationtime:String
    },
    data(){
        return{
            activePhoto:null,           //大图
            activeIndex:null,           //目前的大图下标
        }
    },
    mounted(){
       
    },
    methods:{
        handleUserSearchResultClick(userResult) {
            // 处理用户点击搜索结果的逻辑，可以跳转到用户详情页或执行其他操作
            console.log("Clicked on user search result:", userResult);
            // 跳转到用户详情页
            router.push({ name: 'userDetail', params: { userid: userResult.userid } });
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
<style src="../css/userCard.css" scoped></style>
