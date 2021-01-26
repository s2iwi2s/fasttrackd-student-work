import styled, { css } from 'styled-components';

const UserResultsSection = styled.section`
 padding: 10px 10px;
 width: 96%;
 height: 100%;
 display: flex;
 justify-content: space-around;
 flex-direction: column;
 align-items: center;
 margin: 10px 0;
`

const Stats = styled.div`
 display: flex;
 flex-direction: column;
 width: 100%;
 height: 100%;
 box-shadow: -7px -6px 15px -5px rgba(0, 0, 0, 0.56);
`

const Stat = styled.div`
 display: flex;
 flex-direction: row;
`

const StatLabel = styled.div`
line-height: 25px;
text-align: left;
font-weight: bold;
background-color: #0700000a;
display: flex;
padding: 2px 20px;
width: 50%;
`

const StatValue = styled.span`
line-height: 25px;
text-align: left;
background-color: #0700000a;
display: flex;
padding: 2px 20px;
width: 50%;
`

const Avatar = styled.img`
 border-bottom: 0px;
 border-top-left-radius: 40px;
 border-top-right-radius: 40px;
 box-shadow: -7px -6px 15px -5px rgba(0, 0, 0, 0.56);
 height: auto;
 min-height: 200px;
 width: 100%;
 ${props => props.winner && css`
   border: 5px solid red;
   border-bottom: 0px;
  `}
  ${props => !props.winner && css`
  border: 5px solid inherit;
 `}
`

const UserDetailSpan = styled.span`
padding-left: 20px;
background-color: #07000007;
line-height: 25px;
color: #000000;
width: 90%;
height: 14px;
`
const UsernameSpan = styled(UserDetailSpan)`
padding-top: 30px;
border-radius: 50px 20px 0px 0px;
`

const BioSpan = styled(UserDetailSpan)`
padding-bottom: 20px;
border-radius: 0px 0px 50px 20px;
margin-bottom: 20px;
`

const UserResults = ({ details }) => {

 const StatField = ({ label, value }) => {
  return (
   <Stat>
    <StatLabel>{label}:&nbsp;</StatLabel>
    <StatValue class="value">{value}</StatValue>
   </Stat>
  )
 }
 return (
  <>
   {details && <UserResultsSection>
    <UsernameSpan>{details.username}</UsernameSpan>
    <UserDetailSpan>{details.name}</UserDetailSpan>
    <UserDetailSpan>{details.location}</UserDetailSpan>
    <UserDetailSpan>{details.email}</UserDetailSpan>
    <BioSpan>{details.bio}</BioSpan>
    <Avatar winner={details.winner}
     class="avatar"
     src={details['avatar-url']}
     alt="avatar picture"
    />
    <Stats>
     <StatField label={'Titles'} value={details.titles.join(', ')} />
     <StatField label={'Favorite language'} value={details['favorite-language']} />
     <StatField label={'Total stars'} value={details['total-stars']} />
     <StatField label={'Highest star count'} value={details['highest-starred']} />
     <StatField label={'Public repos'} value={details['public-repos']} />
     <StatField label={'\'Perfect\' Repos'} value={details['perfect-repos']} />
     <StatField label={'Followers'} value={details['followers']} />
     <StatField label={'Following Repos'} value={details['following']} />
    </Stats>
   </UserResultsSection>}
  </>
 )
}
export default UserResults

