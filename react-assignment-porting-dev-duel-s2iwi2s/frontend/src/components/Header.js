import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

const MainHeader = styled.header`
 display: flex;
 flex-direction: row;
 justify-content: space-between;
 margin: 24px 4px;
`

const LinkLogo = styled(NavLink)`
 margin: 0 12px;
 text-decoration: none;
 color: black;
 background-color: transparent;
 font-weight: bold;
 cursor: pointer;
 &:hover{
  text-decoration: underline;
 }
`

const Nav = styled.nav`
 display: block;
`

const LinkBotton = styled(NavLink)`
 margin: 0 12px;
 text-decoration: none;
 color: black;
 &:hover{
  text-decoration: underline;
 }
`
const Header = () => {
 return (
  <MainHeader>
   <div>
    <LinkLogo to={"/"}>&lt;dev-duel&gt;</LinkLogo>
   </div>
   <Nav>
    <LinkBotton to={"/inspect"}>inspect</LinkBotton>
    <LinkBotton to={"/duel"}>duel</LinkBotton>
   </Nav>
  </MainHeader>
 )
}

export default Header