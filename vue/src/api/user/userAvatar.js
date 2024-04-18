import { request } from "../../utils/userRequest"
export function updateUserAvatar(data){
    return request({
        url:`userAvatar`,
        method:'post',
        data:data
    })
}