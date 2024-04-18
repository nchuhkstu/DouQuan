<template>
    <div id="publish">
        <div id="publish-main" v-if="display">
            <div id="publish-head">
                <input id="publish-title" placeholder="请输入标题" v-model="article.title" autocomplete="off">
            </div>
            <input id="fileInput" type="file" style="display: none;" @change="handleFileUpload($event);$event.target.value = ''">
            <div id="publish-body">
                <textarea id="content" placeholder="请输入内容..." v-model="article.content"></textarea>
            </div>
            <div id="publish-foot">
                <div id="emotion">
                    <div id="publish-emotion"></div>
                    <label class="publish-foot-option">表情</label>
                </div>
                <div id="picture">
                    <div id="publish-picture" @click="displayPicture"></div>
                    <label class="publish-foot-option" @click="displayPicture">图片</label>
                </div>
                
                <button id="publish-button" @click="handPublish">发布</button>
            </div>
            <div id="publish-imageContainer">
                <label for="fileInput">
                    <div class="upload-container">
                        <div class="plus-icon">+</div>
                    </div>
                </label>
            </div>
        </div>
        <div id="publish-display" @click="displayMain" v-if="!display"></div>
        <login ref="loginRef"></login>
    </div>
</template>
<script>
import { uploadPhoto } from "../api/photo/photo"
import { publish } from "../api/article/article"
import { postArticlePhoto } from "../api/article/articlePhoto"
import login from '../components/login.vue';
export default{
    name:'publish',
    components:{
        login,
    },
    data(){
        return{
            display:false,
            pictureStatus:false,
            article:{
                title:'',
                content:'',
            },
            i:1,
            j:1,
            images:[],
        }
    },
    methods:{
        displayMain(){
            this.display = !this.display;
        },
        displayPicture() {
            this.pictureStatus = !this.pictureStatus;
            document.getElementById("publish-imageContainer").style.display = this.pictureStatus ? "grid" : "none";
        },
        async handPublish(){
            if(this.article.title == ''){
                alert("标题不能为空");
                return;
            }
            const response = (await publish(this.article));
            if(response.data.code==504){
                this.$refs.loginRef.openModal();
                return;
            }
            const articleid = response.data.data;
            if(this.images.length>0){
                const formData = new FormData();
                for (const image of this.images) {
                    formData.append('files', image);
                }
                const list = (await uploadPhoto(formData)).data.data;
                for(let i=0;i<list.length;i++){
                    await postArticlePhoto({articleid:articleid,photoid:list[i]})
                }
            }
            location.reload();
        },
        //上传图片
        handleFileUpload(event) {
            this.images.push(event.target.files[0]);
            const img = document.createElement('img');
            const file = event.target.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(file);
            reader.onload = (e) =>{
                const image = e.target.result;
                img.style.backgroundImage = 'url('+image+')';
            };
            document.getElementById('publish-imageContainer').insertAdjacentElement('beforeend', img);
            img.style.width = '100px';
            img.style.height = '100px';
            img.style.gridRow = this.i;
            img.style.gridColumn = this.j;
            img.style.backgroundSize = "cover";
            img.style.backgroundRepeat = "no-repeat";
            img.style.backgroundPosition = "center";
            if(this.j==3){
                this.i++;
                this.j=1;
            }else{
                this.j++;
            }
            const uploadContainer = document.querySelector('.upload-container')
            uploadContainer.style.gridRow = this.i;
            uploadContainer.style.gridColumn = this.j;
            if(this.i==4){
                uploadContainer.remove();
            }
        },
    }
}
</script>
<style src="../css/publish.css" scoped></style>