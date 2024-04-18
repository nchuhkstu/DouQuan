import { request } from "../utils/request.js";
export function getHotLadder(data){
    return request({
        url:`/Search/getHotLadder`,
        method:'get',
    })
}
export function getLikeLadder(data){
    return request({
        url:`/Search/getLikeLadder`,
        method:'get',
    })
}
export function getViewLadder(data){
    return request({
        url:`/Search/getViewLadder`,
        method:'get',
    })
}
export function getCommentLadder(data){
    return request({
        url:`/Search/getCommentLadder`,
        method:'get',
    })
}