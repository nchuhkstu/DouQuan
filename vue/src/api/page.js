import { request } from "../utils/request.js";
export function findByPage(pageSize, currentPage) {
    return request({
      url: `/Page/${pageSize}/${currentPage}`,
      method: 'get'
    });
}
export function publish(data){
    return request({
        url:'/Page/publish',
        method:'post',
        data
    });
}
export function like(data){
    return request({
        url:'/Page/like',
        method:'post',
        data
    })
}
export function dislike(data){
    return request({
        url:'/Page/dislike',
        method:'post',
        data
    })
}
export function islike(data){
    return request({
        url:'/Page/islike',
        method:'post',
        data
    })
}
export function findPageByUserid(userid, pageSize, currentPage) {
    return request({
      url: `/Page/getPageByUserid/${userid}/${pageSize}/${currentPage}`,
      method: 'get'
    });
}
