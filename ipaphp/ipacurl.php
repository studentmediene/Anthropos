<?php
	
	function ipa_curl($method, $param){
		
		$cacrt = '/etc/ipa/ca.crt';
	
		$curl = 'curl \
				-H referer:https://ipa.studentmediene.local/ipa \
				-H"Content-Type:application/json" \
				-H "Accept:applicaton/json" --negotiate -u : \
				--delegation always \
				--cacert ' . $cacrt . ' \
				-d \'{"method":"' . $method . '","params":[[],{' . $param . '}],"id":0}\' \
				-X POST https://ipa.studentmediene.local/ipa/json';
		
		$output = shell_exec($curl);
		
		return $output;
	
	}
	
	function get_all_users() {
		$param = '"sizelimit":"99999"';
		return ipa_curl('user-find', $param);
	}
	
	function get_all_usernames() {
		$param = '"sizelimit":"99999","pkey_only":"True"';
		return ipa_curl('user-find', $param);
	}
	
	function get_all_active_users() {
		$param = '"sizelimit":"99999","in_role":"aktiv"';
		return ipa_curl('user-find', $param);
	}
	function get_all_active_usernames() {
		$param = '"sizelimit":"99999","in_role":"aktiv","pkey_only":"True"';
		return ipa_curl('user-find', $param);
	}
	
	function get_all_inactive_users() {
		$param = '"sizelimit":"99999","in_role":"inaktiv"';
		return ipa_curl('user-find', $param);
	}
	
	function get_all_inactive_usernames() {
		$param = '"sizelimit":"99999","in_role":"inaktiv","pkey_only":"True"';
		return ipa_curl('user-find', $param);
	}
	
	function get_user($username) {
		$param = '"uid":"' . $username . '"';
		return ipa_curl('user-find', $param);
	}
	
	function get_all_users_of_group($groupname) {
		$param = '"sizelimit":"99999","in_groups":"' . $groupname . '"';
		return ipa_curl('user-find', $param);
	}
	
	function change_email($username, $new_email){
		$param = '"uid":"' . $username . '", "mail":"' . $new_email . '"';
		return ipa_curl('user_mod', $param);
	}
	
	function change_telephonenumber($username, $new_telephonenumber) {
		$param = '"uid":"' . $username . '", "telephonenumber":"' . $new_telephonenumber . '"';
		return ipa_curl('user_mod', $param);
	}
	
	function change_first_name($username, $new_first_name) {
		$param = '"uid":"' . $username . '", "givenname":"' . $new_first_name . '"';
		return ipa_curl('user_mod', $param);
	}
	
	function change_last_name($username, $new_last_name) {
		$param = '"uid":"' . $username . '", "sn":"' . $new_last_name . '"';
		return ipa_curl('user_mod', $param);
	}
	
	function delete_user($username) {
		$param = '"uid":"' . $username . '"';
		return ipa_curl('user_del', $param);
	}
	
	function delete_group($group) {
		$param = '"cn":"' . $group . '"';
		return ipa_curl('group_del', $param);
	}
	
	function enable_user($username) {
		$param = '"uid":"' . $username . '"';
		return ipa_curl('user_enable', $param);
	}
	function disable_user($username) {
		$param = '"uid":"' . $username . '"';
		return ipa_curl('user_disable', $param);
	}
	
	function add_user_to_group($usernames, $group) {
		$username = '';
		if (is_array($usernames)){
			$length = sizeof($usernames);
			$i = 0;
			foreach ($usernames as $value) {
				$i++;
				$username .= $value;
				if ($length > $i){
					$username .= ',';
				}
			}
		}else {
			$username = $usernames;
		}
		
		$param = '"cn":"' . $group . '","user":"' . $username . '"';
		
		return ipa_curl('group_add_member', $param);
	}
	
	function add_user($username, $password, $first_name, $last_name, $email, $telephonenumber) {
		$param = '"uid":"' . $username . '",';
		$param .= '"userpassword":"' . $password . '",';
		$param .= '"givenname":"' . $first_name . '",';
		$param .= '"sn":"' . $last_name . '",';
		$param .= '"telephonenumber":"' . $telephonenumber . '",';
		$param .= '"mail":"' . $email . '",';
		$param .= '"loginshell":"/bin/sh"';
		
		echo $param;
		
		return ipa_curl('user_add', $param);
	}
	
	function add_user_with_random_password($username, $first_name, $last_name, $email, $telephonenumber) {
		$param = '"uid":"' . $username . '",';
		$param .= '"userpassword":"' . $password . '",';
		$param .= '"givenname":"' . $first_name . '",';
		$param .= '"sn":"' . $last_name . '",';
		$param .= '"telephonenumber":"' . $telephonenumber . '",';
		$param .= '"mail":"' . $email . '",';
		$param .= '"random":"True",'
		$param .= '"loginshell":"/bin/sh"';
		
		echo $param;
		
		return ipa_curl('user_add', $param);
	}
	
	function add_group($groupname, $description) {
		$param = '"cn":"' . $groupname . '","descripion":"' . $description . '"';
		
		return ipa_curl('group_add', $param);
	}
	
?>