<template>
    <div id="container">
        <input id="fileInput" type="file" style="display: none;" @change="handleFileUpload($event)">
        <form>
            <table>
                <tr>
                    <td colspan="4"><label for="fileInput"><div id="avatar" :style="{backgroundImage:'url('+registForm.file+')'}"></div></label></td>
                </tr>
                <tr>
                    <td><label>昵称</label></td>
                    <td colspan="3"><input type="text" v-model="this.registForm.name"></td>
                </tr>
                <tr>
                    <td><label>用户名</label></td>
                    <td colspan="3"><input type="text" v-model="this.registForm.username"></td>
                </tr>
                <tr>
                    <td><label>密码</label></td>
                    <td colspan="3"><input type="text" v-model="this.registForm.password"></td>
                </tr>
                <tr>
                    <td><label>性别</label></td>
                    <td>男<input type="radio" value="男" name="gender" v-model="this.registForm.gender"></td>
                    <td>女<input type="radio" value="女" name="gender" v-model="this.registForm.gender"></td>
                </tr>
                <tr>
                    <td><label>生日</label></td>
                    <td colspan="3"><input type="date" v-model="this.registForm.birthday"></td>
                </tr>
                <tr>
                    <td><label>个性签名</label></td>
                    <td colspan="3"><textarea type="text" v-model="this.registForm.selfintroduction"></textarea></td>
                </tr>
                <tr>
                    <td></td>
                    <td ><el-button @click="handleRegist">注册</el-button></td>
                    
                </tr>
            </table>
        </form>
    </div>
    
</template>
<script>
import { regist } from '../api/user';
import { uploadAvatar } from '../api/image';
import router from "../modules/router"

export default{
    data(){
        return {
            userAccountForm:{
                name:'',
                password:'',
            },
            userExtendForm:{

            },
            registForm:{
                name:'',
                username:'',
                password:'',
                gender:'男',
                birthday:null,
                selfintroduction:'',
            },
            image:'',
        };
    },
    methods:{
        handleFileUpload(event) {
            this.image = event.target.files[0];
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.onload = (e) =>{
                this.registForm.file = e.target.result;
            };
            reader.readAsDataURL(file);
        },
        handleRegist(){
            regist(this.registForm).then(response=>{
                console.log(response);
                const formData = new FormData();
                formData.append('image',this.image);
                uploadAvatar(formData,{ userid: response.data.data.userid  }).then(response => {
                    router.push({ path: '/registAccess', query: { name: this.registForm.name, username: this.registForm.username} });
                })
                router.push({ path: '/registAccess', query: { name: this.registForm.name, username: this.registForm.username} });
                
            });
        },
    },
}
</script>
<style src="../css/regist.css" scoped></style>