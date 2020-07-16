# aws-signature-version-4
Signature Version 4 is the process to add authentication information to AWS requests sent by HTTP. 
<br/>
For security, most requests to AWS must be signed with an access key, which consists of an access key ID and secret access key. These two keys are commonly referred to as your security credentials.
</br>

<p>When you use the <a href="https://aws.amazon.com/cli/" target="_blank"><span>AWS Command Line Interface (AWS CLI)</span><awsui-icon class="awsdocs-link-icon" name="external" initialized="true"><span class="awsui-icon awsui-icon-size-normal awsui-icon-variant-normal"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" focusable="false" aria-hidden="true"><path class="stroke-linecap-square" d="M10 2h4v4"></path><path d="M6 10l8-8"></path><path class="stroke-linejoin-round" d="M14 9.048V14H2V2h5"></path></svg></span></awsui-icon></a> or one of the
                                          <a href="https://aws.amazon.com/tools/" target="_blank"><span>AWS SDKs</span><awsui-icon class="awsdocs-link-icon" name="external" initialized="true"><span class="awsui-icon awsui-icon-size-normal awsui-icon-variant-normal"><svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 16 16" focusable="false" aria-hidden="true"><path class="stroke-linecap-square" d="M10 2h4v4"></path><path d="M6 10l8-8"></path><path class="stroke-linejoin-round" d="M14 9.048V14H2V2h5"></path></svg></span></awsui-icon></a> to make requests to AWS, these tools
                                          automatically sign the requests for you with the security credentials you specify
                                          when you
                                          configure the tools. When you use these tools, you don't need to learn how to sign
                                          requests
                                          yourself. <b>However, when you manually create HTTP requests to access AWS services,
                                          you must sign requests
                                          that require signing yourself.</b>
                                       </p>

<b>How this repo helps to implement Signature Version 4 works</b>

