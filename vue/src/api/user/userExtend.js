import { request } from "../../utils/userRequest"
export function getUser(userid){
    return request({
        url:`userExtend/detail/${userid}`,
        method:'get',
    })
}
export function updateUser(data){
    return request({
        url:'userExtend',
        method:'put',
        data:data,
    })
}