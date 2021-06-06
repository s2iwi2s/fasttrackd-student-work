import { configureStore } from '@reduxjs/toolkit';
import teamsReducer from './slices/teamsSlice';
import userReducer from './slices/userSlice';
import projectReducer from './slices/projectSlice';
import companyReducer from './slices/companySlice'

export default configureStore({
    reducer: {
        teams: teamsReducer,
        user: userReducer,
        project: projectReducer,
        company: companyReducer
    },
});