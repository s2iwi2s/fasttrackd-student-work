import styled from "styled-components"


const Screenshot = styled.img`
height: auto;
width: 400px;
border: 15px solid transparent;
border-radius: 20px;
`
const Content = styled.div`
display: flex;
justify-content: space-around;
line-height: 145%;
width: 100%;
padding: 10px 10px;
`
const LabelContent = styled.div`
display: flex;
flex-direction: column;
padding: 00px 20px;
width: 60%;
`
const LinkContent = styled.div`
display: flex;
flex-direction: column;
padding: 10px 0px;
width: 10%;
`

const Divider = styled.hr`
width: 100%;
margin: 45px 0px;
display: block; 
height: 1px;
border: 0; 
border-top: 1px solid rgba(235, 232, 232, 0.100);
margin: 1em 0; padding: 0;
`

const Blank = styled.div`
width: 400px;
`

const Link = styled.a`
padding: 5px 20px;
margin: 5px 0px;
color: rgb(189, 189, 189);
border-radius: 20px;
width: 100px;
text-decoration: none;
transition: 0.2s;
&:hover {
 color: #555;
 border-bottom: 1px solid transparent;
 background-color: #e67e22;
}
`

const PortfolioLine = ({ img, title, description, github, demo }) => {
 return (
  <>
   <Content>
    {img && <Screenshot src={img} />}
    {!img && <Blank />}
    <LabelContent>
     <h3>{title}</h3>
     <p>{description}</p>

     <LinkContent>
      <h4>Links</h4>
      <Link href={github} target="_blank">GitHub</Link>
      {demo && <Link href={demo} target="_blank">Demo</Link>}
     </LinkContent>
    </LabelContent>
   </Content>
   <Divider />
  </>
 )
}

export default PortfolioLine