import { request } from "../utils/request.js";
export function comment(data){
    return request({
        url:'/comment',
        method:'post',
        data
    })
}
export function getcomment(pageid){
    return request({
        url:`/comment/get/${pageid}`,
        method:'get',
    })
}
export function getcommentNum(pageid){
    return request({
        url:`/comment/getcommentNum/${pageid}`,
        method:'get',
    })
}