import {createSlice} from '@reduxjs/toolkit'

export const projectSlice = createSlice({
    name: 'project',
    initialState: {
        allProjectNames: [''],
        selectedProject: null,
    },
    reducers: {
        setAllProjectNames: (state, action) => {
            state.allProjectNames = action.payload;
        },
        setAllProjectContent: (state, action) => {
            state.allProjectContent = action.payload;
        },
        setSelectedProject: (state, action) => {
            console.log(action.payload);
            state.selectedProject = action.payload;    
        },
    },
});

export const { setAllProjectNames, setAllProjectContent, setSelectedProject } = projectSlice.actions;

export const selectAllProjectNames = state => state.project.allProjectNames;
export const selectAllProjectContent = state => state.project.allProjectContent;
export const selectSelectedProject = state => state.project.selectedProject==null ? null : ({...state.project.selectedProject});

export default projectSlice.reducer;