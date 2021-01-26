import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

const Nav = styled.nav`
 display: flex;
 justify-content: flex-end;
 margin: 0;
 background: #333333;
 padding-bottom: 20px;
`

const NavContent = styled.div`
 display: block;
 width: 1140px;
 margin: 0 auto;
`

const NaviLink = styled(NavLink)`
 padding: 8px 0;
 color: #ffffff;
 text-decoration: none;
 text-transform: uppercase;
 font-size: 90%;
 transition: border-bottom 0.2s;
 &:hover{
  border-bottom: 2px solid #e67e22;
 }
`

const NavUl = styled.ul`
 float: right;
 list-style: none;
 margin-top: 45px;
`

const NavLi = styled.li`
 display: inline-block;
 margin-left: 40px;
`

const Icon = styled.div`
display:table-cell;
height: 70px;
width: 70px;
float: left;
font-size: 225%;
border-radius: 50%;
border: 15px solid transparent;
background: #000000;
text-align: center;
vertical-align:middle;
line-height:45px;
margin-top: 10px;
margin-left: 25px;
`
const Header = () => {
 return (
  <>
   <Nav>
    <NavContent>
     {/* <Avatar src="img/me.jpg" alt="Avatar" /> */}
     <NaviLink to="/aboutme" ><Icon>W</Icon></NaviLink>
     <NavUl>
      <NavLi><NaviLink to="/aboutme" >About Me</NaviLink></NavLi>
      <NavLi><NaviLink to="/mystory">My Story</NaviLink></NavLi>
      <NavLi><NaviLink to="/resume">Resume</NaviLink></NavLi>
      <NavLi><NaviLink to="/portfolio">Portfolio</NaviLink></NavLi>
     </NavUl>
    </NavContent>
   </Nav>
  </>
 )
}

export default Header