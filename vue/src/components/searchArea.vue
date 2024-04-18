<template>
<div id="searchNum">
    总共搜索到{{ userSearchResults.length +articles.length+commentCards.length}}条数据
</div>
<div v-if="userSearchResults.length > 0" id="user-search-results">
    <UserCard v-for="(userCard, index) in userCards" :portraitsrc="userCard.portraitsrc" :userid="userCard.userid" :username="userCard.username" :name="userCard.name" :logincity="userCard.logincity" :selfintroduction="userCard.selfintroduction"/>
</div>
    <div v-else>
        未找到用户。
</div>
<div id="articleArea">
    <Article v-for="(article,index) in articles" :avatarsrc="article.avatarsrc" :name="article.name" :publishTime="article.publishTime" :title="article.title" :content="article.content" :photosrc="article.photosrc" :userid="article.userid" :pageid="article.pageid" :likeNum="article.likeNum" :commentNum="article.commentNum" />
</div>
<div id="comment" v-if="commentSearchResults.length > 0" >
    <commentCard v-for="(commentCard,index) in commentCards" :avatarsrc="commentCard.avatarsrc" :name="commentCard.name" :content="commentCard.content" :time="commentCard.time" :commentid="commentCard.commentid" :pageid="commentCard.pageid"/>
</div>
</template>
  <script>
  import { searchingPage,searchingUser,searchingComment } from "../api/search";
  import { getDetailByUserid } from "../api/user";
  import { getAvatar,getPhoto } from "../api/image";
  import { getcommentNum } from "../api/comment";
  import router from "../modules/router"
  import JSZip from 'jszip';
  import Article from "../components/article.vue";
  import UserCard from "./userCard.vue"
  import commentCard from "./commentCard.vue";
  export default {
    components:{
        Article,         //文章组件
        UserCard,
        commentCard
    },
    data() {
      return {
        articles: [],           //存放文章
        pageSize: 10,     //一次请求查询多少文章
        currentPage:1,          //当前页数
        pageNum:'',             //总页数
        name:null,              //用户昵称
        isFetchingData:false,   //异步函数信号标志
        isSearchBarVisible: true, // 控制搜索栏的显示
        isHeadVisible: false,     // 控制头部的显示
        userSearchResults:"",
        commentSearchResults:"",
        userCards:[],
        commentCards:[]
      };
    },
    mounted(){
        this.handleFindByPage();
        this.handleSearchUser();
        this.handleSearchComment();
    },
    methods: {
        //查询文章
        async handleFindByPage() {
            if (this.isFetchingData) {
                // 如果正在获取数据，则等待
                await new Promise(resolve => setTimeout(resolve, 10000));
            }
            this.isFetchingData = true;
            try {
                const response = await searchingPage(this.searching,this.pageSize, this.currentPage);
                this.pageNum = Math.floor(response.data.data.total / this.pageSize) + 1;
                for (let i = 0; i < response.data.data.total - this.pageSize * (response.data.data.currentPage - 1) && i < this.pageSize; i++) {
                    try {
                        const response1 = await getDetailByUserid({ userid: response.data.data.data[i].userid.toString() });
                        const src = await this.getAvatar(response.data.data.data[i].userid.toString());
                        const photosrc = await this.getPhoto({userid: response.data.data.data[i].userid.toString(), pageid: response.data.data.data[i].pageid.toString()});
                        const publishTime = new Date(response.data.data.data[i].publishtime);
                        const year = (publishTime.getUTCFullYear() === new Date().getUTCFullYear()) ? "" : publishTime.getUTCFullYear() + "/";
                        const month = (publishTime.getUTCMonth() + 1).toString().padStart(2, "0");
                        const day = publishTime.getUTCDate().toString().padStart(2, "0");
                        const hour = publishTime.getUTCHours().toString().padStart(2, "0");
                        const minute = publishTime.getUTCMinutes().toString().padStart(2, "0");
                        const time = `${year}${month}/${day} ${hour}:${minute}`;
                        const s = response.data.data.data[i].likelist;
                        const a = s.split(",");
                        let likeNum = a.length;
                        let commentNum = 0;
                        const commentResponse = await getcommentNum(response.data.data.data[i].pageid);
                        commentNum = commentResponse.data.data;
                        if (s.length == 0) {
                            likeNum = likeNum - 1;
                        }
                        this.articles.push({ 
                            avatarsrc: src,
                            name: response1.data.data.user.name, 
                            publishTime: time, 
                            title: response.data.data.data[i].title, 
                            content: response.data.data.data[i].content,
                            photosrc:photosrc,
                            pageid:response.data.data.data[i].pageid.toString(),
                            likeNum:likeNum.toString(),
                            commentNum:commentNum
                        });
                    } catch (error) {
                        console.error(error);
                    }
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.isFetchingData = false;
            }
        },
        async handleSearchUser() {
            if (this.searching.length > 0) {
                try {
                const response = await searchingUser(this.searching);
                this.userSearchResults = response.data.data; // 存储搜索到的用户数据
                this.handleUserCard();
                } catch (error) {
                console.error(error);
                }
            } else {
                this.userSearchResults = []; // 清空用户搜索结果数组
            }
        },
        handleUserSearchResultClick(userResult) {
            // 处理用户点击搜索结果的逻辑，可以跳转到用户详情页或执行其他操作
            console.log("Clicked on user search result:", userResult);
            // 例如，跳转到用户详情页
            router.push({ name: 'userDetail', params: { userid: userResult.userid } });
        },
        async handleUserCard() {
            try {
                for (let i = 0; i <this.userSearchResults.length; i++) {
                    try {
                        const src = await this.getAvatar(this.userSearchResults[i].userid.toString());
                        this.userCards.push({ 
                            portraitsrc: src,
                            userid: this.userSearchResults[i].userid.toString(),
                            username: this.userSearchResults[i].username, 
                            name: this.userSearchResults[i].name,
                            logincity: this.userSearchResults[i].logincity,
                            selfintroduction: this.userSearchResults[i].selfintroduction});
                    } catch (error) {
                        console.error(error);
                    }
                }
            } catch (error) {
                console.error(error);
            } finally {
                this.isFetchingData = false;
            }
        },
        async handleSearchComment() {
            if (this.searching.length > 0) {
                try {
                const response = await searchingComment(this.searching);
                //this.commentSearchResults = response.data.data;
                this.commentSearchResults=[];
                    for(let i=0;i<response.data.data.length;i++){
                        const avatarsrc = await this.getAvatar(response.data.data[i].userid.toString());
                        let name = null;
                        await getDetailByUserid({userid:response.data.data[i].userid.toString()}).then(response=>{
                            name = response.data.data.user.name;
                        })
                        const content = response.data.data[i].content;
                        const time = response.data.data[i].commenttime;
                        const publishTime = new Date(time);
                        const year = (publishTime.getFullYear() === new Date().getFullYear()) ? "" : publishTime.getFullYear() + "/";
                        const month = (publishTime.getMonth() + 1).toString().padStart(2, "0");
                        const day = publishTime.getDate().toString().padStart(2, "0");
                        const hour = publishTime.getHours().toString().padStart(2, "0");
                        const minute = publishTime.getMinutes().toString().padStart(2, "0");
                        const time2 = `${year}${month}/${day} ${hour}:${minute}`;
                        const commentid = response.data.data[i].commentid.toString();
                        const pageid=response.data.data[i].commentid.toString();
                        this.commentSearchResults.push({avatarsrc:avatarsrc,name:name,content:content,time:time2,commentid:commentid,pageid:pageid});
                        this.commentCards.push({avatarsrc:avatarsrc,name:name,content:content,time:time2,commentid:commentid,pageid:pageid});
                    }
                } catch (error) {
                console.error(error);
                }
            } else {
                this.commentSearchResults = []; // 清空用户搜索结果数组
            }
        },
        handleCommentSearchResultClick(commentResult) {
            // 处理用户点击搜索结果的逻辑，可以跳转到用户详情页或执行其他操作
            console.log("Clicked on commetn search result:", commentResult);
            // 例如，跳转到用户详情页
            router.push({ name: 'Detail', params: { pageid: commentResult.pageid } });
        },
        //下一页
        nextPage(){
            if(this.currentPage<this.pageNum){
                this.currentPage++;
                this.handleFindByPage().then(()=>{
                    this.scrollEnabled = true;
                });
            }
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
        //获取文章的图片
        getPhoto(data) {
            return new Promise((resolve, reject) => {
                getPhoto(data)
                .then(response => {
                    if (response.data.byteLength == 0) {
                    resolve([]); // 返回空的src数组
                    } else {
                    // 使用JSZip解压缩ZIP文件
                    return JSZip.loadAsync(response.data);
                    }
                })
                .then(zip => {
                    if (!zip) {
                    resolve([]); // 返回空的src数组
                    } else {
                    // 存储图片数据的数组
                    const imagePromises = [];
                    
                    // 遍历ZIP文件中的文件
                    zip.forEach((relativePath, file) => {
                        const allowedExtensions = ['.jpg', '.png', '.webp','jpeg','heif'];
                        if (allowedExtensions.some(ext => file.name.toLowerCase().endsWith(ext))) {                            // 读取图片文件数据
                            const imagePromise = file.async('base64').then(data => {
                                return {
                                name: file.name,
                                data: `data:${file.type};base64,${data}`
                                };
                            });
                            imagePromises.push(imagePromise);
                        }
                    });
                    // 等待所有图片数据读取完成
                    Promise.all(imagePromises)
                        .then(images => {
                        // 返回所有图片的src数组
                        const srcArray = images.map(image => image.data);
                        resolve(srcArray);
                        })
                        .catch(error => {
                        reject(error);
                        });
                    }
                })
                .catch(error => {
                    reject(error);
                });
            });
        },
    },
    name:'SearchArea',
    props: {
        searching:String,
    }
  };
  </script>
<style src="../css/searchArea.css" scoped></style>

