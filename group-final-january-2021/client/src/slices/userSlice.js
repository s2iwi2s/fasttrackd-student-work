import { createSlice } from '@reduxjs/toolkit';

const nullUser = {
    username:null,
    role:null,
    team:null,
    company:null
}

const isNullUser = (user) =>{
    if(user==null || (user.username==null || user.role==null || user.team==null || user.company==null)){
        return true;
    }
    return false;
}

export const userSlice = createSlice({
    name: 'user',
    initialState: nullUser,
    reducers: {
        setUser: (state,action) => {
            state.username = action.payload.username;
            state.role = action.payload.role;
            state.team = action.payload.team;
            state.company = action.payload.company;
        },
        setUsername: (state, action) => {
            state.username = action.payload;
        },
        setRole: (state, action) => {
            state.role = action.payload;
        },
        setTeam: (state, action) => {
            state.team = action.payload;
        },
        setCompany: (state, action) => {
            state.company = action.payload;
        },
    },
});

export const {setUser, setUsername, setRole, setTeam, setCompany } = userSlice.actions;

export const selectUsername = state => state.user.username;
export const selectRole = state => state.user.role;
export const selectTeam = state => state.user.team;
export const selectCompany = state => state.user.company;
export const selectUser = state => isNullUser(state.user) ? null : ({...state.user})

export default userSlice.reducer;