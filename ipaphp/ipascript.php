#!/bin/php
<?php
	
	function ipa($method, $param){
	
		$command = 'ipa ' . $method . ' ' . $param;
		
		$output = shell_exec($command);
		
		return $output;
	
	}
	
	function user_search($search_string) {
		$param = $search_string;
		return ipa('user-find', $param);
	}
	
	function change_email($username, $new_email){
		$param = $username . ' --email ' . $new_email;
		return ipa('user-mod', $param);
	}
	
	function change_telephonenumber($username, $new_telephonenumber) {
		$param = $username . ' --phone ' . $new_telephonenumber;
		return ipa('user-mod', $param);
	}
	
	function change_first_name($username, $new_first_name) {
		$param = $username . ' --first ' . $new_first_name;
		return ipa('user-mod', $param);
	}
	
	function change_last_name($username, $new_last_name) {
		$param = $username . ' --last ' . $new_last_name;
		return ipa('user-mod', $param);
	}
	
	function delete_user($username) {
		$param = $username;
		return ipa('user-del', $param);
	}
	
	function delete_group($group) {
		$param = $group;
		return ipa('group-del', $param);
	}
	
	function enable_user($username) {
		$param = $username;
		return ipa('user-enable', $param);
	}
	function disable_user($username) {
		$param = $username;
		return ipa('user-disable', $param);
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
		
		$param = $group . ' --users ' . $username;
		
		return ipa('group-add-member', $param);
	}
	
	function add_user($username, $password, $first_name, $last_name, $email, $telephonenumber) {
		$param = $username;
		$param .= ' --first ' . $first_name;
		$param .= ' --last ' . $last_name;	
		$param .= ' --email ' . $email;
		$param .= ' --phone ' . $telephonenumber;
		$param .= ' --random ';
		
		return ipa('user-add', $param);
	}
	
	function add_group($groupname, $description) {
		$param = $groupname . ' --desc ' . $description;
		
		return ipa('group-add', $param);
	}
	
?>