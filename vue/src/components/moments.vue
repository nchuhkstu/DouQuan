<template>
    <div id="moments">
        <!-- <div id="moments-head">
            <SearchBar  @search-clicked="handleSearchClicked"></SearchBar>
        </div> -->
        <div id="moments-body">
            <div id="moments-left">
                <div>热搜榜</div>
                <div>浏览量榜</div>
                <div>点赞榜</div>
                <div>评论榜</div>
            </div>
            <div id="moments-mid">
                <div v-if="!isSearchMode" id="articleArea">
                    <publish></publish>
                    <Article v-for="article in articles" :key="article" :article="article" @goZone="goZone" :displayDelete="false"/>
                </div>
                <div v-else>
                    <SearchArea :key="searchKey" :searching="searching"></SearchArea>
                </div>
                <div id="moments-mid-foot">滑到底了</div>
            </div>
            <div id="moments-right">
                <ladder></ladder>
            </div>
            
        </div>
        <div id="return-bar">
            <div v-show="showReturnButton" id="return-button" @click="returnToTop">
                返回顶部
            </div>
        </div>
    </div>
</template>
<script>
import SearchArea from "./searchArea.vue";
import Article from "../components/article.vue";
import ladder from "./ladder.vue"
import SearchBar from "./searchBar.vue"
import publish from "./publish.vue";
import { getHot } from "../api/article/article";

export default{
    name:'moments',
    props: {

    },
    data(){
        return{
            articles:[],            //存放文章
            pageSize:10,            //页大小
            currentPage:1,          //当前页
            toltalPage:null,        //总页数
            scrollEnabled:true,     //开启滑轨监听
            isFetchingData:false,   //异步函数信号标志
            showReturnButton: false,
            isSearchBarVisible: true, // 控制搜索栏的显示
            isHeadVisible: false,     // 控制头部的显示
            searching: "", // 搜索框内容
            isSearchMode: false,
            searchKey: 0,
        }
    },
    mounted(){
        window.addEventListener('scroll', this.handleScroll);
        this.handleGetHot();
    },
    methods:{
        handleGetHot(){
            return new Promise((resolve,reject)=>{
                getHot(this.pageSize,this.currentPage).then(response=>{
                    this.totalPage = Math.floor(response.data.data.total / this.pageSize) + 1;
                    for (let i = 0; i < response.data.data.total - this.pageSize * (this.currentPage - 1) && i < this.pageSize; i++){
                        this.articles.push(response.data.data.data[i]);
                    }
                    resolve();
                })
            })
        },
        handleSearchClicked(data) {
        // 处理从 SearchBar 接收到的数据
            this.searching = data;
        // 设置 isSearchMode 为 true，切换到 searchPage
            this.isSearchMode = true;
            this.searchKey += 1;
        },
        //监听滑轨
        handleScroll() {
            const scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            const windowHeight = window.innerHeight;
            const documentHeight = Math.max(
            document.documentElement.clientHeight,
            document.documentElement.scrollHeight,
            document.body.clientHeight,
            document.body.scrollHeight
            );
            if (scrollTop + windowHeight >= documentHeight*0.6) {
                this.showReturnButton=true;
            }
            else{
                this.showReturnButton=false;
            }
            if(!this.scrollEnabled){
                return;
            }
            if (scrollTop + windowHeight >= documentHeight*0.8) {
                this.scrollEnabled = false;
                this.nextPage();
            }
        },
        //下一页
        nextPage(){
            if(this.currentPage<this.totalPage){
                this.currentPage++;
                this.handleGetHot().then(()=>{
                    this.scrollEnabled = true;
                });
            }
        },
        returnToTop() {
            document.documentElement.scrollTop = 0;
            document.body.scrollTop = 0;
            this.showReturnButton=false;
        },
        goZone(userid){
            this.$emit('goZone',userid);
        }
    },
    components:{
        Article,
        SearchArea,
        SearchBar,
        publish,
        ladder,
    },
}
</script>
<style>
#moments{
    width: 100%;
    min-width: 1050px;
    background-color:rgb(241, 242, 245);
    background-color: rgb(17, 17, 17);
    position: relative;
}
#moments-head{
    width: 100%;
    overflow: hidden;
}
#moments-body{
    display: flex;
    justify-content: center;
}
#moments-left{
    position: sticky;
    margin-top: 20px;
    width:150px;
    background-color: rgb(255, 255, 255);
    background-color: rgb(35, 35, 35);
    color: rgb(191,191,191);
    height: 95vh;
    bottom:0;
    top:50px;
    margin-right: 1%;
}
#moments-mid{
    margin-top: 20px;
    position: relative;
    width:50%;
    height: 100%;
    overflow: auto;
    min-width: 650px;
}
#moments-right{
    /* position: fixed;
    z-index: 5;
    right: 0px; */
    position: sticky;
    top: 50px;
    margin-top: 20px;
    width:250px;
    background-color:rgb(255, 255, 255);
    background-color: rgb(35, 35, 35);
    color: rgb(191,191,191);
    height: 95vh;
    margin-left: 1%;
}
#moments-mid-foot{
    width: 100%;
    height: 30px;
    background-color: rgb(35,35,35);
    border-radius: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}
#return-bar {
    position: fixed;
    width: 100%;
    text-align: center;
    bottom: 20px;
    background-color: transparent;
    z-index: 1000; /* 确保在其他元素上方 */
    pointer-events: none;
    min-width: 990px;
}

#return-button {
    width: 100px;
    cursor: pointer;
    background-color: rgb(30,30,30);
    color: rgb(175,175,175);
    padding: 5px;
    margin-left: 44%;
    border-radius: 5px;
    pointer-events: auto;
}
#return-button:hover {
    color: rgb(200,200,200);
}
</style>