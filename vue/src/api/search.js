import { request } from "../utils/request.js";
export function getSearching(data){
    return request({
        url:`/Search/getsearching/${data}`,
        method:'get',
        data
    })
}
  export function searchingPage(data,pageSize, currentPage) {
    return request({
      url: `/Search/searchingPage/${data}/${pageSize}/${currentPage}`,
      method: 'get'
    })
  }
  export function searchingVideo(data,pageSize, currentPage) {
    return request({
      url: `/Search/searchingVideo/${data}/${pageSize}/${currentPage}`,
      method: 'get'
    })
  }
  export function searchingUser(data){
   return request({
      url:`/Search/searchingUser/${data}`,
      method: 'get'
   })
  }
  export function searchingComment(data){
    return request({
       url:`/Search/searchingComment/${data}`,
       method: 'get'
    })
   }
   export function getPage(data){
    return request({
       url:`/Page/getPage/${data}`,
       method: 'get'
    })
   }