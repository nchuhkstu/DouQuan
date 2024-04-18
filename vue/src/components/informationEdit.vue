<template>
    <div id="informationEdit" v-if="modalStatus">
        <div id="informationEdit-content">
            <div id="head">
                编辑资料<div id="informationEdit-close" @click="closeModal"></div>
            </div>
            <div id="information">
                <img id="avatar" :src="avatarUrl" @click="selectAvatar">
                <div class="information-input"><label>昵称</label><input v-model="name"></div>
                <div class="information-input"><label>简介</label><input v-model="selfIntroduction"></div>
                <div class="information-input">
                    <label>性别</label>
                    <select id="gender" v-model="gender">
                        <option value="男">男</option>
                        <option value="女">女</option>
                    </select>
                </div>
                <div class="information-input"><label>生日</label><input v-model="birthday" type="date"></div>
            </div>
            <div id="submit" @click="update">提交</div>
        </div>
    </div>
</template>
<script>
import { uploadPhoto } from '../api/photo/photo';
import { updateUser } from '../api/user/userExtend';
import { updateUserAvatar } from '../api/user/userAvatar';
export default{
    name:'informationEdit',
    props:{
        user:Object,
    },
    data(){
        return{
            modalStatus: false, // 控制模态框显示与隐藏
            avatarUrl:null,
            name:null,
            birthday:null,
            gender:null,
            selfIntroduction:null,
            image:null,
        }
    },
    methods:{
        openModal() {
            this.modalStatus = true;
            this.initData();
        },
        closeModal() {
            this.modalStatus = false;
        },
        selectAvatar(){
            const input = document.createElement('input');
            input.type = 'file';
            input.accept = 'image/*';
            input.onchange = e =>{
                const file = e.target.files[0];
                this.image = file;
                const reader = new FileReader();
                reader.onload = e =>{
                    this.avatarUrl = e.target.result;
                };
                reader.readAsDataURL(file);
            };
            input.click();
        },
        initData(){
            this.avatarUrl=this.user.avatarUrl;
            this.name=this.user.name;
            if(this.user.birthday)
                this.birthday=this.user.birthday.slice(0, 10);
            this.gender=this.user.gender;
            this.selfIntroduction=this.user.selfIntroduction;
        },
        async update(){
            const formData = new FormData();
            formData.append('files',this.image);
            if(this.image!=null){
                const response = await uploadPhoto(formData);
                updateUser({avatarid:response.data.data[0],
                        gender:this.gender,
                        birthday:this.birthday,
                        selfintroduction:this.selfIntroduction,
                        name:this.name
                });
                updateUserAvatar({photoid:response.data.data[0]})
            }
            else{
                updateUser(
                    {gender:this.gender,
                    birthday:this.birthday,
                    selfintroduction:this.selfIntroduction,
                    name:this.name}
                )
            }
            location.reload();
        }
    },
}
</script>
<style scoped>
#informationEdit{
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
#informationEdit-content{
    width: 30vw;
    background-color: rgb(242,242,242);
    border-radius: 2vh;
}
#head{
    width: 100%;
    height: 5vh;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    border-bottom: 0.3vh solid rgb(232,232,232);
}
#informationEdit-close{
    position: absolute;
    right: 1vw;
    cursor: pointer;
    width: 4vh;
    height: 4vh;
    background-size: 100% 100%;
    background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAJAAAACQCAYAAADnRuK4AAAACXBIWXMAACxLAAAsSwGlPZapAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAPTSURBVHgB7d29ThRRGIDh7wyLWGlFQoJ7A4rBBgs7CxPs8QqwsRMra3u8Am/AYGOnnR2yGxNZxM4KsgnQURrY43y7IQgL7PzPnHPeJzFCmGLIvMz/OSsCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAoCxGPLGwsNSemjJrVuxzY+SOtWZPrN0UidZ7va19aQBdxygyqyaSZRHbtlaOxcqXJq1jWl4E9PDR4xfG2ncazuWfaUgnYld/b3d3pUaLi0sPrMinq9ZRDcS83vnZ2RDHOB/QcM/Tku83LaN/6SciK3VFNCkeNdobRc9c2xNF4rgokjeTltEN14o34P14Q0rFksSj9OfG2Im/S9M4H1C8D11OtFgNESWN54yev4ljnA8o6cY5W7aqiNLGo9Is2xTu74FEUp0zVBFRlnhG4itHx7gf0PAyOJ0yI8oez/AQtimOcT6g01vyYXgFk1IZEeWJZ7j3mYnWxTHOB/Sr292L70Ws1B1RrnhMvO7TdrW35d7NRG/uROc6dOS8T5Q7npasbHfrvdGZlTcBqToiCjke5VVAqsqIQo9HeReQqiIi4hnxMiBVZkTEc87bgFQZERHPRV4HpIqMiHjGeR+QKiKiaf2aeMYEEZDKG5H+TzzjgglI5XvUkIHn8aigAlKVRRRAPCq4gFTpEQUSjwoyIFVaRAHFo4INSBUeUWDxqKADUoVFFGA8KviAVO6IAo1HtQS52YHIyV8JEoewgg5hdQ9erAsn0QWeRIcYEZfxBV/GhxYRNxJLEFJEPMooSSgR8TC1RCFE5MPQ5kTyvgzWlMGLTRNEQEW8SdiEwYtNxCutN7l0h7nOwYtNxUv117nm8QQRXcSwnqtMeLZFROcYWHhZwgejRDTC0Ob/pXyqTkRMrnAu4ysZoUfE9C4q5/s8IUfkRUBNGDEaakReTDQeTQ033D1Jq+A3CXMPXmSi8erpRONNiEdtx3uQPHesJbIvxTE+TDT+RNIq8R3mXBHZZJOmN4kPE42n2/tU8AJ85oiMbYtjnA8o1UaqcPREloiy7LXq5sMhLNlE4zUMvUkdkUk/aXrdnA9oMC3vJ26gGsdtpYrIwYnGp8Rxh/3+8ezc/FF03af2NGDQ38FB/2hubv5bvC5P42/HL/HjdTy18nbnR8e5jzpwPiB1eNDfnW3Pb5iBfuaW6InoTPxv34r5GP9Vr/U6nT9SM40oXsevw3UUuSsakr7pOJDPcjt65WI8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAQJX+ATc9oF5n57RZAAAAAElFTkSuQmCC);
}
#information{
    width: 100%;
    text-align: center;
}
#avatar{
    height: 15vh;
    width: 15vh;
    border-radius: 50%;
    margin:2vh auto;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    cursor: pointer;
}
.information-input{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 95%;
    background-color: white;
    height: 4vh;
    margin:0 auto;
    margin-bottom: 2vh;
}
label{
    width: 5vw;
    text-align: center;
}
input{
    width: calc(100% - 5vw);
    font-size: 2vh;
    outline: none;
    border: none;
}
select{
    width: calc(100% - 5vw);
    font-size: 2vh;
    outline: none;
    border: none;
}
option{

}
#submit{
    height: 4vh;
    width: 95%;
    background-color: rgb(0,153,255);
    margin: 3vh auto;
    border-radius: 1.5vh;
    display: flex;
    align-items: center;
    justify-content: center;
    color: white;
}
#submit:hover{
    background-color: rgb(0,141,255);
}
</style>