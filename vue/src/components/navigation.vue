<template>
    <div id="navigation">
        <div id="navigation-left">
            <img id="logoImage" src="../../images/sys/抖圈.jpg">
        </div>
        <div id="search-block">
            <searchBox  @search-clicked="handleSearchClicked"></searchBox>
        </div>
        <div id="navigation-mid">
            <label></label>
            <img id="navigation-moments" @click="setActiveTab('moments')" src="../../images/sys/home.png">
            <img id="navigation-tiktok" @click="setActiveTab('tiktok')" src="../../images/sys/shortVideo.png">
            <img id="navigation-live" @click="setActiveTab('live')" src="../../images/sys/live.png">
            <label></label>
        </div>
        <div id="navigation-right">
            <div id="navigation-information">
                <img id="navigation-right-avatar" src="../../images/sys/默认头像.jpg">
                <div id="navigation-right-name" class="navigation" @click="login">{{name}}</div>
            </div>
            <div id="navigation-information-options">
                <div class="navigation-information-option" @click="setActiveTab('zone')">个人空间</div>
                <div class="navigation-information-option" @click="logout">注销</div>
            </div>
        </div>
        <login ref="loginRef"></login>
    </div>
</template>
<script>
import { islogin } from '../api/user/userAccount';
import { getUser } from '../api/user/userExtend';
import login from './login.vue';
import searchBox from './searchBox.vue';
export default{
    name:'navigation',
    components:{
        login,
        searchBox,
    },
    props:{
        
    },
    data(){
        return{
            name:'请登录',
            loginStatus:false,
        }
    },
    mounted(){
        this.islogin();
    },
    methods:{
        setActiveTab(tabName) {
            const moments = document.getElementById('navigation-moments');
            const tiktok = document.getElementById('navigation-tiktok');
            const live = document.getElementById('navigation-live');
            moments.style.borderBottom = tabName === 'moments' ? '3px solid orange' : 'none';
            tiktok.style.borderBottom = tabName === 'tiktok' ? '3px solid orange' : 'none';
            live.style.borderBottom = tabName === 'live' ? '3px solid orange' : 'none';
            this.$emit('changeComponents', tabName);
        },
        login(){
            if(this.loginStatus==false)
                this.$refs.loginRef.openModal();
        },
        logout(){
            // 不需要告诉后端，例如将token加入黑名单等，只需要自己删掉
            localStorage.removeItem('token');
            location.reload();
        },
        islogin(){
            islogin().then(response=>{
                if(response.data.code==200){
                    this.loginStatus=true;
                    this.informationlisten();
                    const userid = response.data.data;
                    getUser(userid).then(response=>{
                        document.getElementById('navigation-right-avatar').src = response.data.data.avatarUrl;
                        document.getElementById('navigation-right-name').textContent = response.data.data.name;
                    })
                }
            })
        },
        informationlisten(){
            const navigationInformation = document.getElementById('navigation-information');
            const navigationInformationOptions = document.getElementById('navigation-information-options');
            navigationInformation.addEventListener('mouseenter', function() {
                navigationInformationOptions.style.display = 'block';
            });
            navigationInformation.addEventListener('mouseleave', function() {
                navigationInformationOptions.style.display = 'none';
            });
            navigationInformationOptions.addEventListener('mouseenter', function() {
                navigationInformationOptions.style.display = 'block';
            });
            navigationInformationOptions.addEventListener('mouseleave', function() {
                navigationInformationOptions.style.display = 'none';
            });
        },
        handleSearchClicked(data) {
            this.$emit('goSearch', data);
            this.setActiveTab(null);
        },
    },
}
</script>
<style>
#navigation{
    width: 100%;
    background-color: rgb(35,35,35);
    height: 100%;
    display: flex;
}
#navigation-left{
    display: flex;
    width: 10%;
}
#logoImage{
    height: 50px;
}
#search-block{
    width:25%;
}
#navigation-mid{
    width: 50%;
    height: 100%;
    border-radius: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
#navigation-moments{
    height: 50px;
    width: 50px;
    cursor: pointer;
    border-bottom: 3px solid orange;
}
#navigation-tiktok{
    height: 50px;
    width: 50px;
    cursor: pointer;
}
#navigation-live{
    height: 50px;
    width: 50px;
    cursor: pointer;
}
#navigation-right{
    width:25%;
    height: 100%;
    min-height: 50px;
    display: flex;
    align-items: center;
    position: relative;
}
#navigation-information{
    width: 100%;
    height: 100%;
    min-height: 50px;
    display: flex;
    align-items: center;
}
#navigation-right-avatar{
    height: 50px;
    width:50px;
    border-radius: 50%;
}
#navigation-right-name{
    margin-left: 10px;
    color: rgb(191, 191, 191);
    position: relative;
}
#navigation-information-options{
    position: absolute;
    top: 100%;
    left: 0%;
    background-color: rgb(35, 35, 35);
    width: 140px;
    text-align:  center;
    color: rgb(191, 191, 191);
    border-bottom-left-radius: 20px;
    border-bottom-right-radius: 20px;
    display: none;
}
.navigation-information-option{
    padding-top: 10px;
    padding-bottom: 10px;
}
.navigation-information-option:hover{
    background-color: white;
    color: black;
}
</style>