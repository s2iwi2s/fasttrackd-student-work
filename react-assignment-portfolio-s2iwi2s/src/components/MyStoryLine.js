import styled from "styled-components"


const Content = styled.div`
display: flex;
justify-content: space-around;
`
const Avatar = styled.img`
height: 350px;
width: auto;
border: 15px solid transparent;
border-radius: 50%;
`
const Divider = styled.hr`
width: auto;
margin: 45px 0px;
display: block; 
height: 1px;
border: 0; 
border-top: 1px solid rgba(235, 232, 232, 0.100);
margin: 1em 0; padding: 0;
`
const TextContent = styled.div`
line-height: 145%;
display: flex;
flex-direction: column;
justify-content: center;
width: 100%;
`

const MyStoryLine = ({ direction, image, children }) => {

  return (
    <>
      <Content>
        {direction === 'left' &&
          <Avatar src={image} alt="Avatar" />}

        <TextContent>{children}</TextContent>

        {direction === 'right' &&
          <Avatar src={image} alt="Avatar" />}
      </Content>
      <Divider />
    </>)
}

export default MyStoryLine