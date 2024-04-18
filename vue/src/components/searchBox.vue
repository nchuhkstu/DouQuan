<template>
    <div id="search-box">
        <div id="search-border">
            <span class="searchImg"><i class="woo-font woo-font--search"></i>
            </span>
            <input v-model="searchQuery" type="text" placeholder="搜索喜欢的内容" id="search" @input="handleSearchInput" autocomplete="off"><!----><!---->
            <button id="clearInput" v-show="searchQuery" @click="clearSearchInput">
                <svg width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path d="M4.4 4.55l.07-.08a.75.75 0 01.98-.07l.08.07L12 10.94l6.47-6.47a.75.75 0 111.06 1.06L13.06 12l6.47 6.47c.27.27.3.68.07.98l-.07.08a.75.75 0 01-.98.07l-.08-.07L12 13.06l-6.47 6.47a.75.75 0 01-1.06-1.06L10.94 12 4.47 5.53a.75.75 0 01-.07-.98l.07-.08-.07.08z"></path>
                </svg>
            </button>
            <button type="submit" id="search-button" @click="goSearch()">
                <svg width="24" height="24" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path d="M10 2.75a7.25 7.25 0 015.63 11.82l4.9 4.9a.75.75 0 01-.98 1.13l-.08-.07-4.9-4.9A7.25 7.25 0 1110 2.75zm0 1.5a5.75 5.75 0 100 11.5 5.75 5.75 0 000-11.5z"></path></svg>
                搜索
            </button>
        </div>
        <div v-if="searchQuery.length > 0&&searchQuery!=''&&this.searchResults.length!=0" v-show="isShow" id="search-results" >
            <div  v-for="(result, index) in searchResults" :key="index" class="search-result-item" @click="handleSearchResultClick(result)">
                {{ result }}
            </div>
        </div>
    </div>
    </template>
    <script>
      import { getSearching } from "../api/search";
      export default {
        name:'searchBox',
        data() {
          return {
            searchQuery: "", // 搜索框内容
            searchResults: ['输入你想要搜索的内容'],
            isShow: false
          };
        },
        mounted(){
            document.addEventListener('click', this.handleDocumentClick);
        },
        methods: {
            handleDocumentClick(event) {
                // 检查点击事件是否发生在搜索框或搜索容器内
                if (!this.$el.contains(event.target) ||event.target.classList.contains('search')) {
                    this.isShow = false;
                }
                else{
                    this.isShow = true;
                }
            },
            goSearch(){
                if(this.searchQuery.length>0){
                    this.$emit('search-clicked', this.searchQuery);
                    
                }
                // if(this.searchQuery!=null&&this.searchQuery!=''){
                //     router.push({path:'/searchPage',query:{searching:this.searchQuery,userid:this.$route.query.userid,name:this.$route.query.name,}}).then(()=>{
                //     })
                // }
            },
            clearSearchInput() {
                this.searchQuery = "";
            },
            //获取搜索
            getSearching(data) {
                return new Promise((resolve, reject) => {
                    getSearching(data)
                    .then(response => {
                        if (response.data.byteLength == 0) {
                        resolve([]); // 返回空的src数组
                        } else {
                        this.searchResults = response.data.data; // 假设响应是一个包含搜索结果的数组
                        console.log(this.searchResults.length);
                        return response.data;
                        }
                    })
                    .catch(error => {
                        reject(error);
                    });
                });
            },
            async handleSearchInput() {
            if (this.searchQuery.length > 0) {
                // 发送请求到数据库，根据搜索框内容查找结果
                try {
                await this.getSearching(this.searchQuery);
                } catch (error) {
                console.error(error);
                }
            } else {
                this.searchResults = ['']; // 清空搜索结果数组
            }
            },
            handleSearchResultClick(result) {
                // 在这里处理点击搜索结果的逻辑，可以跳转到相应的页面或执行其他操作
                console.log("Clicked on search result:", result);
                this.searchQuery=result;
                this.$emit('search-clicked', this.searchQuery);
            },
        },
      };
      </script>
    
<style src="../css/searchBox.css" scoped></style>