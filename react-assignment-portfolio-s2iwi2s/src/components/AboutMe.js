import { Link } from "react-router-dom"
import styled from "styled-components"
import { BiLike } from "react-icons/bi";
import { FiTwitter } from "react-icons/fi";
import Template from "./Template"

const Avatar = styled.img`
height: 450px;
width: auto;
float: left;
border-radius: 50%;
border: 15px solid transparent;
`
const Content = styled.div`
display: flex;
justify-content: space-around;
`
const TextContent = styled.div`
display: flex;
flex-direction: column;
justify-content: center;
line-height: 145%;
width: 100%;
`
const FbLink = styled(Link)`
background: #1877f2;
border-radius: 30px;
color: #ffffff;
font-size: 70%;
text-decoration: none;
padding: 10px 10px;
min-width: 100px;
`
const TwLink = styled(FbLink)`
background: #1b95e0;
margin-left: 20px;
`
const AboutMe = () => {
 return (
  <Template title="about me">
   <Content>
    <TextContent>
     <p><span className={"hightlight"}>Winston Pidor</span> is a skilled fullstack java developer whose strong work ethic, attention to detail and adaptability makes him a valuable addition to any project. He is a graduate of the FastTrack’D Java and JavaScript Developer program with a bachelor’s degree in Computer Engineering. Winston has displayed skills in developing, deploying, troubleshooting, and maintaining Java applications. He has achieved a productive level of knowledge and skill in numerous Java frameworks such as Struts I &amp; II, Spring Framework and Spring MVC(Springboot), Hibernate, JDBC, HTML, CSS, Javascript, React and Angular. Winston brings a positive outlook and many years of development experience to any team of which he is a member.
</p>
     {/* <p><span className={"hightlight"}>Winston Pidor</span> is a highly skilled java developer with more than 10 years of professional work experience. He is independent, resourceful(capable of devising ways and means) and has strong analytical and troubleshooting skills. He is able to work on a fast paced, team-oriented environment.</p>
     <p>He Specialized in developing software using Java, Struts 1 and 2, Spring / SpringBoot / Spring WS / Spring Rest, JDBC/SQL, Oracle, MongoDb, PostgreSql, MySql, Hibernate, Html, CSS and Javascript.</p>
     <p>He Has Exposure to Angular, React, Docker, and AWS.</p>
     <p>And, has experience with tools like Eclipse, GIT, SVN, CVS, ANT and Maven. </p> */}
    </TextContent>
    <Avatar src="img/me.jpg" alt="Avatar" />
   </Content>
   {/* <FbLink><BiLike /> Like</FbLink>
   <TwLink><FiTwitter /> Follow @s2i77</TwLink> */}
  </Template>
 )
}

export default AboutMe