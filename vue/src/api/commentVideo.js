import { request } from "../utils/request.js";
export function commentVideo(data){
    return request({
        url:'/commentVideo',
        method:'post',
        data
    })
}
export function getcommentVideo(videoid){
    return request({
        url:`/commentVideo/get/${videoid}`,
        method:'get',
    })
}
export function getcommentNumVideo(videoid){
    return request({
        url:`/commentVideo/getcommentNum/${videoid}`,
        method:'get',
    })
}
  