import {createSlice} from '@reduxjs/toolkit'

export const companySlice = createSlice({
    name: 'company',
    initialState: {
        companyNames: ["Company 1"],
        companiesContent: ["company 1"],
        selectedCompany: null,
    },
    reducers: {
        setCompanyNames: (state, action) => {
            state.companyNames = action.payload;
        },
        setCompaniesContent: (state, action) => {
            state.companiesContent = action.payload;
        },
        setSelectedCompany: (state, action) => {
            console.log(action.payload);
            state.selectedCompany = action.payload;    
        },
    },
});

export const { setCompanyNames, setCompaniesContent, setSelectedCompany } = companySlice.actions;

export const selectCompanyNames = state => state.company.companyNames;
export const selectCompaniesContent = state => state.company.companiesContent;
export const selectSelectedCompany = state => state.company.selectedCompany==null ? null : ({...state.company.selectedCompany});

export default companySlice.reducer;