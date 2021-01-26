## Entities:

	User
		{ // User
		  username: 'string',
		  profile: 'Profile',
		  joined: 'timestamp'
		}
	Profile
		{ // Profile
		  firstName?: 'string',
		  lastName?: 'string',
		  email: 'string',
		  phone?: 'string'
		}
	Credentials
		{ // Credentials
		  username: 'string',
		  password: 'string'
		}
	Hashtag
		{ // Hashtag
		  label: 'string',
		  firstUsed: 'timestamp',
		  lastUsed: 'timestamp'
		}
	Tweet
		{ // Tweet
		  id: 'integer'
		  author: 'User',
		  posted: 'timestamp',
		  content?: 'string',
		  inReplyTo?: 'Tweet',
		  repostOf?: 'Tweet'
		}
	Context
		{ // Context
		  target: 'Tweet',
		  before: ['Tweet'],
		  after: ['Tweet']
		}

## End Points
	validate
		GET validate/tag/exists/{label}
			response: boolean
		GET validate/username/exists/@{username}
			response: boolean
		GET validate/username/available/@{username}
			response: boolean
	users
		GET users
			response - ['User']
		POST users
			Request
				{
				  credentials: 'Credentials',
				  profile: 'Profile'
				}
			Response
				'User'
		GET users/@{username}
			Response
				'User'
		PATCH users/@{username}
			Request
				{
				  credentials: 'Credentials',
				  profile: 'Profile'
				}
			Response
				'User'
		DELETE users/@{username}
			Request - 'Credentials'
			Response - 'User'
		POST users/@{username}/follow
			Request - 'Credentials'
		POST users/@{username}/unfollow
			Request - 'Credentials'
		GET users/@{username}/feed
			Response - ['Tweet']
		GET users/@{username}/tweets
			Response - ['Tweet']
		GET users/@{username}/mentions
			Response - ['Tweet']
		GET users/@{username}/followers
			Response - ['User']
		GET users/@{username}/following
			Response - ['User']
	tags
		GET tags
			Response - ['Hashtag']
		GET tags/{label}
			Response - ['Tweet']
		GET tweets
			Response - ['Tweet']
		POST tweets
			Request
				{
				  content: 'string',
				  credentials: 'Credentials'
				}
			Response - 'Tweet'
	tweets
		DELETE tweets/{id}
			Request - 'Credentials'
			Response - 'Tweet'
		POST tweets/{id}/like
			Request - 'Credentials'
		POST tweets/{id}/reply
			Request
				{
				  content: 'string',
				  credentials: 'Credentials'
				}
			Response - 'Tweet'
		POST tweets/{id}/repost
			Request - 'Credentials'
			Response - 'Tweet'
		GET tweets/{id}/tags
			Response - ['Hashtag']
		GET tweets/{id}/likes
			Response - ['User']
		GET tweets/{id}/context
			Response - 'Context'
		GET tweets/{id}/replies
			Response - ['Tweet']
		GET tweets/{id}/reposts
			Response - ['Tweet']
		GET tweets/{id}/mentions
			Response - ['User']
