import axios from 'axios'


export const sendUserAuthRequest = async (username, password) => {
    let response = await axios.post("http://localhost:8080/login", {
        username: username,
        password: password
    })
    let user = {
            username: response.data.username,
            role: response.data.role.name,
            team: response.data.profile.team.name,
            company: {
                name: response.data.profile.company.name,
                content: response.data.profile.company.text
            } 
        }
    return user
}

export const sendEditUserProfileRequest = async (username, password) => {
    return {
        username: 'NewUsername'
    }
}

