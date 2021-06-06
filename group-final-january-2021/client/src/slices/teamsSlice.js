import { createSlice } from '@reduxjs/toolkit';

export const teamsSlice = createSlice({
    name: 'teams',
    initialState: {
        allTeamNames: [''],
        allTeamContent : ['', '', ''],
        selectedTeam: null,
    },
    reducers: {
        setAllTeamNames: (state, action) => {
            state.allTeamNames = action.payload;
        },
        setSelectedTeam: (state, action) => {
            state.selectedTeam = action.payload;    
        },

        setAllTeamContent: (state, action) => {
            state.allTeamContent = action.payload
        }
    },
});

export const { setAllTeamNames, setSelectedTeam, setAllTeamContent} = teamsSlice.actions;

export const selectAllTeamNames = state => state.teams.allTeamNames;
export const selectAllTeamContent = state => state.teams.allTeamContent;
export const selectSelectedTeam = state => state.teams.selectedTeam==null ? null : ({...state.teams.selectedTeam});

export default teamsSlice.reducer;