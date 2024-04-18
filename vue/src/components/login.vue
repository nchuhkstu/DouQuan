<template>
    <div id="login-container" v-if="modalStatus">
        <div id="login-content">
            <div id="login-close" @click="closeModal"></div>
            <div id="login-title">登录解锁全部功能</div>
            <div id="login-title-2">
                <label>动态视频均可行</label>
                <label>点赞评论随心发</label>
                <label>直播间畅聊打赏</label>
            </div>
            <div id="login-form">
                <div id="login-form-content">
                    <div class="login-form-input-container">
                        <input class="login-form-input" type="text" v-model="username" placeholder="账号">
                    </div>
                    <div class="login-form-input-container">
                        <input class="login-form-input" type="password" v-model="password" placeholder="密码">
                    </div>
                    <div id="login-form-regist-container">
                        <label id="login-form-regist" @click="handleClickButton1">{{button1}}</label>
                    </div>
                    <div>
                        <button id="login-form-button" @click="handleClickButton2">{{button2}}</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import {login,regist} from "../api/user/userAccount"
import md5 from 'js-md5';
export default {
    name:'login',
    data() {
        return {
            modalStatus: false, // 控制模态框显示与隐藏
            username:this.$route.query.username,
            password:'',
            button1:'注册',
            button2:'登录',
        };
    },
    methods: {
        handleClickButton1(){
            if(this.button1=='注册'){
                this.button1='返回';
                this.button2='注册';
            }
            else if(this.button1=='返回'){
                this.button1='注册';
                this.button2='登录';
            }
        },
        handleClickButton2(){
            if(this.button2=='登录'){
                const encryptedPassword = md5(this.password).substring(0, 30); // 使用MD5加密密码
                login({name:this.username,password:encryptedPassword}).then(response =>{
                    if(response.data.code == 200){
                        const token=response.data.data;
                        localStorage.setItem('token', token);
                        location.reload();
                    }
                })
            }
            else if(this.button2=='注册'){
                regist({name:this.username,password:this.password}).then(response=>{
                    if(response.data.code == 200){
                        console.log("注册成功");
                        this.button2='前往登录';
                    }
                    if(response.data.code == 503){
                        console.log("账号已存在");
                    }
                })
            }
            else{
                this.button1='注册';
                this.button2='登录';
            }
        },
        openModal() {
            this.modalStatus = true;
        },
        closeModal() {
            this.modalStatus = false;
        },
    },
}
</script>
<style scoped>
#login-container {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
}
#login-content {
    position: relative;
    width: 300px;
    background-color:rgb(220, 220, 220);
    padding: 20px;
}
#login-close{
    position: absolute;
    top: 10px;
    right: 10px;
    cursor: pointer;
    width: 36px;
    height: 36px;
    background-size: 100% 100%;
    background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJAAAACQCAYAAADnRuK4AAAACXBIWXMAACxLAAAsSwGlPZapAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAPTSURBVHgB7d29ThRRGIDh7wyLWGlFQoJ7A4rBBgs7CxPs8QqwsRMra3u8Am/AYGOnnR2yGxNZxM4KsgnQURrY43y7IQgL7PzPnHPeJzFCmGLIvMz/OSsCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAoCxGPLGwsNSemjJrVuxzY+SOtWZPrN0UidZ7va19aQBdxygyqyaSZRHbtlaOxcqXJq1jWl4E9PDR4xfG2ncazuWfaUgnYld/b3d3pUaLi0sPrMinq9ZRDcS83vnZ2RDHOB/QcM/Tku83LaN/6SciK3VFNCkeNdobRc9c2xNF4rgokjeTltEN14o34P14Q0rFksSj9OfG2Im/S9M4H1C8D11OtFgNESWN54yev4ljnA8o6cY5W7aqiNLGo9Is2xTu74FEUp0zVBFRlnhG4itHx7gf0PAyOJ0yI8oez/AQtimOcT6g01vyYXgFk1IZEeWJZ7j3mYnWxTHOB/Sr292L70Ws1B1RrnhMvO7TdrW35d7NRG/uROc6dOS8T5Q7npasbHfrvdGZlTcBqToiCjke5VVAqsqIQo9HeReQqiIi4hnxMiBVZkTEc87bgFQZERHPRV4HpIqMiHjGeR+QKiKiaf2aeMYEEZDKG5H+TzzjgglI5XvUkIHn8aigAlKVRRRAPCq4gFTpEQUSjwoyIFVaRAHFo4INSBUeUWDxqKADUoVFFGA8KviAVO6IAo1HtQS52YHIyV8JEoewgg5hdQ9erAsn0QWeRIcYEZfxBV/GhxYRNxJLEFJEPMooSSgR8TC1RCFE5MPQ5kTyvgzWlMGLTRNEQEW8SdiEwYtNxCutN7l0h7nOwYtNxUv117nm8QQRXcSwnqtMeLZFROcYWHhZwgejRDTC0Ob/pXyqTkRMrnAu4ysZoUfE9C4q5/s8IUfkRUBNGDEaakReTDQeTQ033D1Jq+A3CXMPXmSi8erpRONNiEdtx3uQPHesJbIvxTE+TDT+RNIq8R3mXBHZZJOmN4kPE42n2/tU8AJ85oiMbYtjnA8o1UaqcPREloiy7LXq5sMhLNlE4zUMvUkdkUk/aXrdnA9oMC3vJ26gGsdtpYrIwYnGp8Rxh/3+8ezc/FF03af2NGDQ38FB/2hubv5bvC5P42/HL/HjdTy18nbnR8e5jzpwPiB1eNDfnW3Pb5iBfuaW6InoTPxv34r5GP9Vr/U6nT9SM40oXsevw3UUuSsakr7pOJDPcjt65WI8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQJX+ATc9oF5n57RZAAAAAElFTkSuQmCC);
}
#login-title{
    text-align: center;
    font-size: 18px;
    font-weight: 500;
    font-family: PingFang SC,DFPKingGothicGB-Medium,sans-serif;
    line-height: 34px;
}
#login-title-2{
    font-size: 10px;
    display: flex;
    align-items: center;
    justify-content: space-between;
}
#login-form{
    background-color: white;
    border-radius: 10px;
    margin-top: 10px;
    
}
#login-form-content{
    padding: 0 50px;
}
.login-form-input-container{
    /* text-align: center; */
    padding-top: 20px;
}
.login-form-input{
    width: 100%;
    height: 36px;
    border: 0;
    outline: none;
    /* padding: 0 10px; */
    background-color: rgb(242, 242, 244);
    border-radius: 10px;
}
#login-form-regist-container{

    margin-top: 10px;
}
#login-form-regist{
    font-size: 12px;
    color: rgb(131,131,131);
    text-decoration: underline;
    cursor: pointer;
}
#login-form-button{
    width: 100%;
    margin-top: 15px;
    margin-bottom: 30px;
    border-radius: 10px;
    background-color: rgb(254, 44, 85);
    color: white;
    height: 36px;
    border: 0;
    outline: none;
    cursor: pointer;
}
</style>